package it15ns.friendscom.grpc.runnables;

import io.grpc.serverPackage.LoginResponse;
import io.grpc.serverPackage.RegisterRequest;
import io.grpc.serverPackage.SearchForProfileRequest;
import io.grpc.serverPackage.SearchForProfileResponse;
import io.grpc.serverPackage.SearchUserRequest;
import io.grpc.serverPackage.SearchUserResponse;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.activities.RegisterActivity;
import it15ns.friendscom.activities.SearchProfileActivity;
import it15ns.friendscom.grpc.CustomCredentials;
import it15ns.friendscom.grpc.GrpcRunnable;
import it15ns.friendscom.handler.LocalUserHandler;

/**
 * Created by danie on 05.06.2017.
 */

public class SearchProfileRunnable implements GrpcRunnable {
    private String searchString;
    private SearchProfileActivity activity;

    public SearchProfileRunnable(String searchString, SearchProfileActivity activity) {
        this.searchString = searchString;
        this.activity = activity;
    }

    @Override
    public Object execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {

        final SearchUserResponse response;
        SearchUserRequest request = SearchUserRequest.newBuilder()
                .setQuery(searchString)
                .build();

        String token = LocalUserHandler.getToken();
        response = blockingStub.withCallCredentials(new CustomCredentials(token)).searchUser(request);

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activity.searchResult(response);
            }
        });
        return true;
    }
}
