package it15ns.friendscom.grpc.runnables;

import io.grpc.serverPackage.LoginRequest;
import io.grpc.serverPackage.LoginResponse;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.activities.LoginActivity;
import it15ns.friendscom.grpc.GrpcRunnable;

/**
 * Created by danie on 05.06.2017.
 */

public class LoginRunnable implements GrpcRunnable {
    private String username;
    private String password;
    private LoginActivity activity;

    public LoginRunnable(String username, String password, LoginActivity activity) {
        this.username = username;
        this.password = password;
        this.activity = activity;
    }

    @Override
    public Object execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {
        return attempLogin(blockingStub);
    }

    public boolean attempLogin(ServerServiceGrpc.ServerServiceBlockingStub blockingStub) {
        LoginRequest loginRequest;
        final LoginResponse loginResponse;

        loginRequest = LoginRequest.newBuilder()
                .setNickname(username)
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