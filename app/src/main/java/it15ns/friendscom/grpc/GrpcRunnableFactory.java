package it15ns.friendscom.grpc;

import io.grpc.serverPackage.LoginReply;
import io.grpc.serverPackage.LoginRequest;
import io.grpc.serverPackage.LoginResponse;
import io.grpc.serverPackage.RegisterReply;
import io.grpc.serverPackage.RegisterRequest;
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
            final LoginResponse loginResponse;

            loginRequest = LoginRequest.newBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .build();


            loginResponse = blockingStub.login(loginRequest);


            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.loginResult(loginResponse);
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
            final LoginResponse response;
            RegisterRequest request = RegisterRequest.newBuilder()
                    .setUsername(username)
                    .setPassword(password)
                    .build();

            response = blockingStub.register(request);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.registerResult(response);
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

