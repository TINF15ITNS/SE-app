package it15ns.friendscom.grpc;

import io.grpc.serverPackage.ServerServiceGrpc;

/**
 * Created by danie on 02/05/2017.
 */

public interface GrpcInvoker {
    void requestComplete(Object response);
    //String getToken();
}
