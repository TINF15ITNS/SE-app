package it15ns.friendscom.grpc;

import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.serverPackage.ServerServiceGrpc;

/**
 * Created by danie on 02/05/2017.
 */

public class GrpcSyncTask {
    // Für Emulator
    private static String mHost = "10.0.2.2";
    // Für VPN
    //private static String mHost = "192.168.1.43";
    private static int mPort = 50051;

    private static ManagedChannel mChannel;

    public static Object execute(GrpcRunnable runnable) {
        try {
            mChannel = ManagedChannelBuilder.forAddress(mHost, mPort).usePlaintext(true).build();
            ServerServiceGrpc.ServerServiceBlockingStub blockingStub = ServerServiceGrpc.newBlockingStub(mChannel);
            ServerServiceGrpc.ServerServiceStub stub = ServerServiceGrpc.newStub(mChannel);

            Object response = runnable.execute(blockingStub, stub);
            mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);

            return response;
        } catch (Exception ex) {
            Log.d("GrpcTask", ex.getMessage());
            return false;
        }
    }
}
