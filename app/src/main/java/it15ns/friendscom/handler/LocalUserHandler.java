package it15ns.friendscom.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;

import java.sql.Date;
import java.util.Calendar;

import io.grpc.serverPackage.GetUserDetailsResponse;
import io.grpc.serverPackage.Response;
import it15ns.friendscom.grpc.GrpcInvoker;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcSyncTask;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.grpc.runnables.DeleteProfileRunnable;
import it15ns.friendscom.grpc.runnables.GetUserDetailsRunnable;
import it15ns.friendscom.model.User;
import it15ns.friendscom.xmpp.XMPPClient;

/**
 * Created by valentin on 5/9/17.
 */

public class LocalUserHandler {

    private static LocalUserHandler instance  = new LocalUserHandler();
    private String token = "";
    private User localUser;

    private LocalUserHandler() {}

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        instance.token = sharedPrefs.getString("token", "");

        if(instance.getToken() == "")
            return false;

        return true;
    }

    public static boolean deleteProfile(Context context, String password) {
        XMPPClient.disconnect();
        SharedPreferences sharedPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        sharedPrefs.edit().putString("token", "").commit();
        sharedPrefs.edit().putString("username", "").commit();

        SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
        sqLiteHandler.deleteAllTables();
        UserHandler.flashInstance();
        GroupHandler.flashInstance();
        LocalUserHandler.flashInstance();
        Response response = (Response) GrpcSyncTask.execute(new DeleteProfileRunnable(password));
        return response.getSuccess();
    }

    public static void logoutProfile(Context context) {
        XMPPClient.disconnect();
        SharedPreferences sharedPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        sharedPrefs.edit().putString("token", "").commit();
        sharedPrefs.edit().putString("username", "").commit();
        UserHandler.flashInstance();
        GroupHandler.flashInstance();
        LocalUserHandler.flashInstance();
        SQLiteHandler sqLiteHandler = new SQLiteHandler(context);
        sqLiteHandler.deleteAllTables();
    }

    public static User getLocalUser(Context context) {
         if(instance.localUser == null) {
             SharedPreferences sharedPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE);
             String username = sharedPrefs.getString("username", "");
             instance.localUser = new User(username);

             GetUserDetailsResponse user = (GetUserDetailsResponse) GrpcSyncTask.execute(new GetUserDetailsRunnable(username));
             if(user.getSuccess() == true)  {
                 instance.localUser.setName(user.getName());
                 instance.localUser.setSurname(user.getSurname());
                 instance.localUser.setTelNumber(user.getPhone());
                 instance.localUser.setMail(user.getEmail());
                 try {
                     Calendar calendar = Calendar.getInstance();
                     long timeInMillis = user.getBirthday();
                     calendar.setTimeInMillis(timeInMillis);
                     instance.localUser.setBirthday(calendar);
                 } catch (Exception ex) {
                     Log.d("LocalUserHandler", "Birthday not valid: " + ex.getMessage() );
                 }
             } else {
                 Log.d("LocalUserHandler", "Cant get user details");
             }
         }

         return instance.localUser;
    }

    public static void flashInstance() {
        instance = new LocalUserHandler();
    }
    // Getter und Setter
    public static void setLocalUser(User localUser){
        instance.localUser = localUser;
    }
    public static String getToken() {
        return instance.token;
    }
    public static void setToken(String token) {
        instance.token = token;
    }
}
