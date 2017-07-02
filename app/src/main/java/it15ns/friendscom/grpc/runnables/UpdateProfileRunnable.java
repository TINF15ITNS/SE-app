package it15ns.friendscom.grpc.runnables;

import io.grpc.serverPackage.Response;
import io.grpc.serverPackage.SearchUserRequest;
import io.grpc.serverPackage.SearchUserResponse;
import io.grpc.serverPackage.ServerServiceGrpc;
import io.grpc.serverPackage.UpdateProfileRequest;
import it15ns.friendscom.activities.ProfileActivity;
import it15ns.friendscom.activities.SearchProfileActivity;
import it15ns.friendscom.grpc.CustomCredentials;
import it15ns.friendscom.grpc.GrpcRunnable;
import it15ns.friendscom.handler.LocalUserHandler;
import it15ns.friendscom.model.User;

/**
 * Created by danie on 05.06.2017.
 */

public class UpdateProfileRunnable implements GrpcRunnable {
    private User localUser;
    private ProfileActivity activity;

    public UpdateProfileRunnable(User localUser, ProfileActivity activity) {
        this.localUser = localUser;
        this.activity = activity;
    }

    @Override
    public Object execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {

        final Response response;
        UpdateProfileRequest request = UpdateProfileRequest.newBuilder()
                .setBirthday(localUser.getBirthday() != null ? localUser.getBirthday().getTimeInMillis() : 0)
                .setEmail(localUser.geteMail())
                .setName(localUser.getName())
                .setPhone(localUser.getTelNumber())
                .setSurname(localUser.getSurname())
                .build();

        String token = LocalUserHandler.getToken();
        response = blockingStub.withCallCredentials(new CustomCredentials(token)).updateProfile(request);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.updateResult(response);
            }
        });
        return true;
    }
}
