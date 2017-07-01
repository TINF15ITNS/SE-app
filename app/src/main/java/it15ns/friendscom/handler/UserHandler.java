package it15ns.friendscom.handler;

import android.content.Context;
import android.util.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.grpc.serverPackage.GetUserDetailsResponse;
import it15ns.friendscom.grpc.GrpcInvoker;
import it15ns.friendscom.grpc.GrpcRunnableFactory;
import it15ns.friendscom.grpc.GrpcSyncTask;
import it15ns.friendscom.grpc.GrpcTask;
import it15ns.friendscom.model.User;

/**
 * Created by danie on 31/05/2017.
 */

public class UserHandler implements GrpcInvoker {
    private static UserHandler instance = new UserHandler();
    private static SQLiteHandler sqLiteHandler;
    private HashMap<String, User> users;

    private UserHandler() {
        users = null;
    }

    // Metoden für die Freundesliste
    public static void addUser(User user) {
        instance.users.put(user.getNickname(), user);
    }

    public static void removeUser(User user) {
        if(instance.users.containsKey(user.getNickname()))
            instance.users.remove(user.getNickname());
    }

    public static User createUser(String nickname, Context context) {
        User user = new User(nickname);
        instance.users.put(nickname, user);
        return user;
    }

    // seach for a user using the nickname
    public static User getUser(String nickname, Context context) {
        if(nickname.equals(LocalUserHandler.getLocalUser().getNickname())) {
            return LocalUserHandler.getLocalUser();
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
        for (User user: getUsers(context)) {
            new GrpcSyncTask(GrpcRunnableFactory.getGetUserDetailsRunnable(user.getNickname(), UserHandler.instance)).run();
        }
    }

    @Override
    public void requestComplete(Object response) {
        GetUserDetailsResponse user = (GetUserDetailsResponse) response;
        if(user.getSuccess() == true)  {

            User localUser = instance.users.get(user.getNickname());
            localUser.setName(user.getName());
            localUser.setSurname(user.getSurname());
            localUser.setTelNumber(user.getPhone());
            localUser.setMail(user.getEmail());
            try {
                Date birthday = Date.valueOf(user.getBirthday());
                localUser.setBirthday(birthday);
            } catch (Exception ex) {
                Log.d("LocalUserHandler", "Birthday not valid: " + ex.getMessage() );
            }
        }
    }
}
