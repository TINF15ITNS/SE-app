package it15ns.friendscom.grpc;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.R;

/**
 * Created by danie on 02/05/2017.
 */

public class GrpcSyncTask {
    private static int port = 50051;

    private static ManagedChannel managedChannel;
    private static Context context;

    public static void setContext(Context applicationContext)
    {
        context = applicationContext;
    }
    public static Object execute(GrpcRunnable runnable) {
        String IPADRESS =  context.getResources().getString(R.string.ipaddress);
        try {
            managedChannel = ManagedChannelBuilder.forAddress(IPADRESS, port).usePlaintext(true).build();
            ServerServiceGrpc.ServerServiceBlockingStub blockingStub = ServerServiceGrpc.newBlockingStub(managedChannel);
            ServerServiceGrpc.ServerServiceStub stub = ServerServiceGrpc.newStub(managedChannel);

            Object response = runnable.execute(blockingStub, stub);
            managedChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);

            return response;
        } catch (Exception ex) {
            Log.d("GrpcTask", ex.getMessage());
            return false;
        }
    }
}
