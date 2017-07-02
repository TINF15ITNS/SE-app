package it15ns.friendscom.grpc;

import io.grpc.serverPackage.ServerServiceGrpc;

/**
 * Created by danie on 02/05/2017.
 */

public interface GrpcRunnable {
    Object execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub);
    //String getToken();
}
