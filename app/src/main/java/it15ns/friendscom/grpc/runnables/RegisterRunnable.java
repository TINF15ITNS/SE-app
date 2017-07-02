package it15ns.friendscom.grpc.runnables;

import io.grpc.serverPackage.LoginResponse;
import io.grpc.serverPackage.RegisterRequest;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.activities.RegisterActivity;
import it15ns.friendscom.grpc.GrpcRunnable;

/**
 * Created by danie on 05.06.2017.
 */

public class RegisterRunnable implements GrpcRunnable {
    private String nickname;
    private String password;
    private RegisterActivity activity;

    public RegisterRunnable(String nickname, String password, RegisterActivity activity) {
        this.nickname = nickname;
        this.password = password;
        this.activity = activity;
    }

    @Override
    public Object execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {
        //throw new RuntimeException();
        final LoginResponse response;
        RegisterRequest request = RegisterRequest.newBuilder()
                .setNickname(nickname)
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
