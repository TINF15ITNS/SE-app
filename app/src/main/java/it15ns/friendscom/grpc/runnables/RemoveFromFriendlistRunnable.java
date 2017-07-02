package it15ns.friendscom.grpc.runnables;

import io.grpc.serverPackage.AddFriendToFriendlistRequest;
import io.grpc.serverPackage.ByNicknameRequest;
import io.grpc.serverPackage.EmptyRequest;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.grpc.CustomCredentials;
import it15ns.friendscom.grpc.GrpcRunnable;
import it15ns.friendscom.handler.LocalUserHandler;

/**
 * Created by danie on 05.06.2017.
 */

public class AddToFriendlistRunnable implements GrpcRunnable {
    private String nickname;

    public AddToFriendlistRunnable(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public Object execute(ServerServiceGrpc.ServerServiceBlockingStub blockingStub, ServerServiceGrpc.ServerServiceStub stub) {
        ByNicknameRequest request = ByNicknameRequest.newBuilder().setNickname(nickname).build();

        String token = LocalUserHandler.getToken();
        return blockingStub.withCallCredentials(new CustomCredentials(token)).addFriendToFriendlist(request);
    }
}
