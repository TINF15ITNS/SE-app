package it15ns.friendscom.grpc.runnables;

import io.grpc.serverPackage.GetUserDetailsRequest;
import io.grpc.serverPackage.GetUserDetailsResponse;
import io.grpc.serverPackage.SearchUserRequest;
import io.grpc.serverPackage.SearchUserResponse;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.activities.ProfileActivity;
import it15ns.friendscom.activities.SearchProfileActivity;
import it15ns.friendscom.grpc.CustomCredentials;
import it15ns.friendscom.grpc.GrpcInvoker;
import it15ns.friendscom.grpc.GrpcRunnable;
import it15ns.friendscom.handler.LocalUserHandler;

/**
 * Created by danie on 05.06.2017.
 */

public class GetUserDetailsRunnable implements GrpcRunnable {
    private String nickname;
    private GrpcInvoker receiver;

    public GetUserDetailsRunnable(String nickname, GrpcInvoker invoker) {
        this.nickname = nickname;
        receiver = invoker;
    }

    @Override
    public boolean execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {

        final GetUserDetailsResponse response;
        GetUserDetailsRequest request = GetUserDetailsRequest.newBuilder()
                .setUserNickname(nickname)
                .build();

        String token = LocalUserHandler.getToken();
        response = blockingStub.withCallCredentials(new CustomCredentials(token)).getUserDetails(request);

        receiver.requestComplete(response);

        return true;
    }
}
