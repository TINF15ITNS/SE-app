package it15ns.friendscom.grpc;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.R;

/**
 * Created by danie on 02/05/2017.
 */

public class GrpcTask extends AsyncTask<Void, Void, Boolean> {
    // Für Emulator
    //private String IPADRESS = "10.0.2.2";

    //private static final String IPADRESS = Resources.getSystem().getString(R.string.ipaddress);
    private int port = 50051;

    private ManagedChannel managedChannel;
    private GrpcRunnable grpcRunnable;
    private Context context;

    public GrpcTask(GrpcRunnable runnable, Context context) {
        this.grpcRunnable = runnable;
        this.context = context;
    }

    @Override
    protected Boolean doInBackground(Void... nothing) {
        String IPADRESS =  context.getResources().getString(R.string.ipaddress);
        try {
            managedChannel = ManagedChannelBuilder.forAddress(IPADRESS, port).usePlaintext(true).build();
            ServerServiceGrpc.ServerServiceBlockingStub blockingStub = ServerServiceGrpc.newBlockingStub(managedChannel);
            ServerServiceGrpc.ServerServiceStub stub = ServerServiceGrpc.newStub(managedChannel);

            boolean success = (boolean) grpcRunnable.execute(blockingStub, stub);
            return success;
        } catch (Exception ex) {
            Log.d("GrpcTask", ex.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        try {
            managedChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
