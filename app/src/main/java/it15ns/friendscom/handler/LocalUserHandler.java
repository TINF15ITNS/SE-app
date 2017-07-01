package it15ns.friendscom.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.sql.Date;

import io.grpc.serverPackage.GetUserDetailsResponse;
import it15ns.friendscom.grpc.GrpcInvoker;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcSyncTask;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.model.User;

/**
 * Created by valentin on 5/9/17.
 */

public class LocalUserHandler implements GrpcInvoker{
    private static LocalUserHandler instance  = new LocalUserHandler();

    private String token = "";
    private User localUser;

    private LocalUserHandler() {
        localUser = new User("Me");
    }

    private LocalUserHandler(User localUser){
        this.localUser = localUser;
    }

    public static void init(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        String username = sharedPrefs.getString("username", "");

        new GrpcSyncTask(GrpcRunnableFactory.getGetUserDetailsRunnable(username, instance)).run();
    }

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        instance.token = sharedPrefs.getString("token", "");
        String username = sharedPrefs.getString("username", "");

        if(!username.equals("") && instance.getLocalUser().getNickname().equals("Me")) {
            instance.getLocalUser().setNickname(username);
        }

        if(instance.getToken() == "" || instance.getLocalUser().getNickname() == "")
            return false;

        return true;
    }

    // Getter und Setter
    public static void setLocalUser(User localUser){
        instance.localUser = localUser;
    }
    public static User getLocalUser() {
         return instance.localUser;
    }

    public static String getToken() {
        return instance.token;
    }

    public void requestComplete(Object response) {
        GetUserDetailsResponse user = (GetUserDetailsResponse) response;
        if(user.getSuccess() == true)  {
            instance.localUser.setName(user.getName());
            instance.localUser.setSurname(user.getSurname());
            instance.localUser.setTelNumber(user.getPhone());
            instance.localUser.setMail(user.getEmail());
            try {
                Date birthday = Date.valueOf(user.getBirthday());
                instance.localUser.setBirthday(birthday);
            } catch (Exception ex) {
                Log.d("LocalUserHandler", "Birthday not valid: " + ex.getMessage() );
            }
        } else {
            Log.d("LocalUserHandler", "Cant get user details");
        }




    }
}
