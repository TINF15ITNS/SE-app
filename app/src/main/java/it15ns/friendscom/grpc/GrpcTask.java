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

public class GrpcTask extends AsyncTask<Void, Void, Boolean> {
    private final GrpcRunnable mGrpc;

    // Für Emulator
    //private String IPADRESS = "10.0.2.2";

    //private String mHost = "192.168.1.43";
    private static final String IPADRESS = "141.72.191.147";
    // Für VPN
    //private String mHost = "192.168.1.24";
    private int mPort = 50051;

    private String mPassword;
    private String mEmail;

    private ManagedChannel mChannel;

    public GrpcTask(GrpcRunnable grpc) {
        this.mGrpc = grpc;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Boolean doInBackground(Void... nothing) {
        try {
            mChannel = ManagedChannelBuilder.forAddress(IPADRESS, mPort).usePlaintext(true).build();
            ServerServiceGrpc.ServerServiceBlockingStub blockingStub = ServerServiceGrpc.newBlockingStub(mChannel);
            ServerServiceGrpc.ServerServiceStub stub = ServerServiceGrpc.newStub(mChannel);


            boolean logs = (boolean) mGrpc.execute(blockingStub, stub);

            //Log.d("joup", logs);
            return logs;
        } catch (Exception ex) {
            Log.d("GrpcTask", ex.getMessage());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        try {
            mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
