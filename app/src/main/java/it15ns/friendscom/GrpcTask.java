package it15ns.friendscom;

import android.os.AsyncTask;
import android.util.Log;

import java.io.Console;
import java.util.concurrent.TimeUnit;

import io.grpc.CallCredentials;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.internal.GrpcUtil;
import io.grpc.serverPackage.ServerServiceGrpc;

/**
 * Created by danie on 02/05/2017.
 */

public class GrpcTask extends AsyncTask<Void, Void, String> {
    private final GrpcRunnable mGrpc;
    private String mHost = "10.0.2.2";
    private int mPort = 50051;

    private String mPassword;
    private String mEmail;

    private ManagedChannel mChannel;

    GrpcTask(GrpcRunnable grpc) {
        this.mGrpc = grpc;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Void... nothing) {
        try {
            mChannel = ManagedChannelBuilder.forAddress(mHost, mPort).usePlaintext(true).build();
            ServerServiceGrpc.ServerServiceBlockingStub blockingStub = ServerServiceGrpc.newBlockingStub(mChannel);
            ServerServiceGrpc.ServerServiceStub stub = ServerServiceGrpc.newStub(mChannel);

            String logs = mGrpc.execute(blockingStub, stub);

            //Log.d("joup", logs);
            return logs;
        } catch (Exception ex) {
            return "Failed... : " + ex.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            mChannel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
