package it15ns.friendscom.grpc.runnables;

import io.grpc.serverPackage.DeleteUserRequest;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.grpc.CustomCredentials;
import it15ns.friendscom.grpc.GrpcRunnable;
import it15ns.friendscom.handler.LocalUserHandler;

/**
 * Created by danie on 05.06.2017.
 */

public class DeleteProfileRunnable implements GrpcRunnable {
    private String password;

    public DeleteProfileRunnable(String password) {
        this.password = password;
    }

    @Override
    public Object execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {
        DeleteUserRequest request = DeleteUserRequest.newBuilder().setPassword(password). build();

        String token = LocalUserHandler.getToken();
        return blockingStub.withCallCredentials(new CustomCredentials(token)).deleteUser(request);
    }
}
