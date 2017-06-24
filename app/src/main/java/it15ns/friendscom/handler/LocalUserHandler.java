package it15ns.friendscom.handler;

import android.content.Context;
import android.content.SharedPreferences;

import it15ns.friendscom.model.User;

/**
 * Created by valentin on 5/9/17.
 */

public class LocalUserHandler {
    private static LocalUserHandler instance  = new LocalUserHandler();

    private String token = "";
    private User localUser;

    private LocalUserHandler() {
        localUser = new User("Me");
    }

    private LocalUserHandler(User localUser){
        this.localUser = localUser;
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
}
