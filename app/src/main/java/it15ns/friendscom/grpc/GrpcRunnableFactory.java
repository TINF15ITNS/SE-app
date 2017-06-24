package it15ns.friendscom.grpc;

import io.grpc.serverPackage.LoginResponse;
import io.grpc.serverPackage.LoginRequest;
import io.grpc.serverPackage.RegisterRequest;
import io.grpc.serverPackage.ServerServiceGrpc;
import it15ns.friendscom.activities.LoginActivity;
import it15ns.friendscom.activities.RegisterActivity;
import it15ns.friendscom.activities.SearchProfileActivity;
import it15ns.friendscom.grpc.runnables.LoginRunnable;
import it15ns.friendscom.grpc.runnables.RegisterRunnable;
import it15ns.friendscom.grpc.runnables.SearchProfileRunnable;

/**
 * Created by danie on 02/05/2017.
 */

public class GrpcRunnableFactory {

    private GrpcRunnableFactory() {};

    public static LoginRunnable getLoginRunnable(String username, String password, LoginActivity activity) {
        return new LoginRunnable(username, password, activity);
    }

    public static RegisterRunnable getRegisterRunnable(String username, String password, RegisterActivity activity) {
        return new RegisterRunnable(username, password, activity);
    }

    public static SearchProfileRunnable getSearchProfileRunnable(String searchstring, SearchProfileActivity activity) {
        return new SearchProfileRunnable(searchstring, activity);
    }
}
