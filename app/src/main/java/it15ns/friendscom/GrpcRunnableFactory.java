package it15ns.friendscom;

import android.content.Context;
import android.content.Intent;

import io.grpc.serverPackage.LoginReply;
import io.grpc.serverPackage.LoginRequest;
import io.grpc.serverPackage.ServerServiceGrpc;

/**
 * Created by danie on 02/05/2017.
 */

public class GrpcRunnableFactory {

    private GrpcRunnableFactory() {};

    public static GrpcLoginRunnable getLoginRunnable(String username, String password, LoginActivity activity) {
        return new GrpcLoginRunnable(username, password, activity);
    }

    private static class GrpcLoginRunnable implements GrpcRunnable {
        private String username;
        private String password;
        private LoginActivity activity;

        GrpcLoginRunnable(String username, String password, LoginActivity activity) {
            this.username = username;
            this.password = password;
            this.activity = activity;
        }

        @Override
        public String execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {
            return attempLogin(blockingStub);
        }

        public String attempLogin(ServerServiceGrpc.ServerServiceBlockingStub blockingStub) {
            LoginRequest loginRequest;
            final LoginReply loginReply;

            loginRequest = LoginRequest.newBuilder()
                    .setUser(username)
                    .setPassword(password)
                    .build();

            loginReply = blockingStub.login(loginRequest);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.loginResult(loginReply);
                }
            });

            return "";
        }
    }
}

/**
 *
 CallCredentials credentials = null;

 if(mGrpc.getToken() == "")
 {

 } else {
 credentials = new CustomCredentials(mGrpc.getToken());
 }
 */

