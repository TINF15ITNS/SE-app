package it15ns.friendscom.grpc;

import io.grpc.serverPackage.LoginReply;
import io.grpc.serverPackage.LoginRequest;
import io.grpc.serverPackage.RegisterReply;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.activities.LoginActivity;
import it15ns.friendscom.activities.RegisterActivity;

/**
 * Created by danie on 02/05/2017.
 */

public class GrpcRunnableFactory {

    private GrpcRunnableFactory() {};

    public static GrpcLoginRunnable getLoginRunnable(String username, String password, LoginActivity activity) {
        return new GrpcLoginRunnable(username, password, activity);
    }

    public static GrpcRegisterRunnable getRegisterRunnable(String username, String password, RegisterActivity activity) {
        return new GrpcRegisterRunnable(username, password, activity);
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
        public boolean execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {
            return attempLogin(blockingStub);
        }

        public boolean attempLogin(ServerServiceGrpc.ServerServiceBlockingStub blockingStub) {
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

            return true;
        }
    }

    private static class GrpcRegisterRunnable implements  GrpcRunnable {
        private String username;
        private String password;
        private RegisterActivity activity;

        GrpcRegisterRunnable(String username, String password, RegisterActivity activity) {
            this.username = username;
            this.password = password;
            this.activity = activity;
        }
        @Override
        public boolean execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {
            //throw new RuntimeException();
            final RegisterReply reply  =  null;

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.registerResult(reply);
                }
            });
            return true;
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

