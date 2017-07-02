package it15ns.friendscom.grpc.runnables;

import io.grpc.serverPackage.SearchUserRequest;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.grpc.CustomCredentials;
import it15ns.friendscom.grpc.GrpcRunnable;
import it15ns.friendscom.handler.LocalUserHandler;

/**
 * Created by danie on 05.06.2017.
 */

public class SyncSearchProfileRunnable implements GrpcRunnable {
    private String searchString;

    public SyncSearchProfileRunnable(String searchString) {
        this.searchString = searchString;
    }

    @Override
    public Object execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {
        
        SearchUserRequest request = SearchUserRequest.newBuilder()
                .setQuery(searchString)
                .build();

        String token = LocalUserHandler.getToken();
        return blockingStub.withCallCredentials(new CustomCredentials(token)).searchUser(request);
    }
}
