package it15ns.friendscom.handler;

import android.content.Context;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.grpc.serverPackage.GetFriendListResponse;
import io.grpc.serverPackage.GetUserDetailsResponse;
import io.grpc.serverPackage.Response;
import it15ns.friendscom.grpc.GrpcInvoker;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcSyncTask;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.grpc.runnables.AddToFriendlistRunnable;
import it15ns.friendscom.grpc.runnables.GetFriendlistRunnable;
import it15ns.friendscom.grpc.runnables.GetUserDetailsRunnable;
import it15ns.friendscom.grpc.runnables.RemoveFromFriendlistRunnable;
import it15ns.friendscom.model.User;

/**
 * Created by danie on 31/05/2017.
 */

public class UserHandler {
    private static UserHandler instance = new UserHandler();
    private static SQLiteHandler sqLiteHandler;
    private HashMap<String, User> users;

    private UserHandler() {
        users = null;
    }

    // Metoden für die Freundesliste
    public static boolean addUser(User user) {
        Response response = (Response) GrpcSyncTask.execute(new AddToFriendlistRunnable(user.getNickname()));
        if(response.getSuccess()) {
            instance.users.put(user.getNickname(), user);
            return true;
        } else
            return false;
    }

    public static boolean removeUser(User user) {
        Response response = (Response) GrpcSyncTask.execute(new RemoveFromFriendlistRunnable(user.getNickname()));
        if(response.getSuccess()) {
            instance.users.remove(user.getNickname());
            return true;
        } else
            return false;
    }

    public static User createUser(String nickname, Context context) {
        User user = new User(nickname);
        instance.users.put(nickname, user);
        return user;
    }

    // seach for a user using the nickname
    public static User getUser(String nickname, Context context) {
        if(nickname.equals(LocalUserHandler.getLocalUser(context).getNickname())) {
            return LocalUserHandler.getLocalUser(context);
        }

        if(instance.users == null) {
            loadUsers(context);
        }

        if(instance.users.containsKey(nickname)) {
            return instance.users.get(nickname);
        } else {
            return createUser(nickname, context);
        }
    }

    public static List<User> getUsers(Context context) {
        if(instance.users == null) {
            loadUsers(context);
        }

        List<User> userList =  new ArrayList<>();

        // gehe durch alle user und füge den user in die queue ein
        Iterator it = instance.users.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            User user = (User) pair.getValue();
            userList.add(user);
        }

        return userList;
    }

    public static List<User> getSortedUsers(Context context) {
        if(instance.users == null) {
            loadUsers(context);
        }

        List<User> users =  new ArrayList<>();

        // gehe durch alle user und füge den user in die queue ein
        Iterator it = instance.users.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            User user = (User) pair.getValue();
            users.add(user);
        }

        // sortieren nach dem neusten Datum
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getSurname().compareTo(user2.getSurname());
            }
        });

        return users;
    }

    private static void loadUsers(Context context) {
        sqLiteHandler = new SQLiteHandler(context);
        instance.users = new HashMap<>();
        instance.users = sqLiteHandler.getAllUsers(context);
        GetFriendListResponse response = (GetFriendListResponse) GrpcSyncTask.execute(new GetFriendlistRunnable(LocalUserHandler.getLocalUser(context).getNickname()));
        if(response.getSuccess() == true) {
            for (String nickname:response.getFriendListList()) {
                if(!instance.users.containsKey(nickname))
                    createUser(nickname, context);
            }
        }
    }

    public static User getUserDetails(String nickname) {
        User user = instance.users.get(nickname);
        GetUserDetailsResponse response = (GetUserDetailsResponse) GrpcSyncTask.execute(new GetUserDetailsRunnable(user.getNickname()));
        if(response.getSuccess() == true)  {
            user.setName(response.getName());
            user.setSurname(response.getSurname());
            user.setTelNumber(response.getPhone());
            user.setMail(response.getEmail());
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(response.getBirthday());
                user.setBirthday(calendar);
            } catch (Exception ex) {
                Log.d("LocalUserHandler", "Birthday not valid: " + ex.getMessage() );
            }
        }

        return user;
    }

    public static void flashInstance() {
        instance = new UserHandler();
    }
}
