package it15ns.friendscom.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by danie on 15/05/2017.
 */

public class Handler {

    private static Handler instance;
    List<Group> groups;

    User me;

    private Handler(){
        //me = getMe();
        groups = new ArrayList<Group>();
        //users = new HashMap<String, User>();
    }

    /**
    public static Handler getInstance() {
        if(instance==null) {
            instance = new Handler();
        }
        return instance;
    }*/

    /*
    // Methoden für the Userverwaltung
    // Methoden für die verwaltung seines eigenen Benutzers
    public User getMe() {

        if(me == null)
            me = new User("Me");

        return me;
    }

    public void setMe(User me) {
        //TODO: grpc call
        this.me = me;
    }

    // Metoden für die Freundesliste
    public void addUser(User user) {
        users.put(user.getNickname(), user);
    }

    public void removeUser(User user) {
        if(users.containsKey(user.getNickname()))
            users.remove(user.getNickname());
    }

    // seach for a user using the nickname
    public User getUser(String nickname) {
        if(users.containsKey(nickname)) {
            return users.get(nickname);
        } else {
            User user = new User(nickname);
            users.put(nickname, user);
            return user;
        }
    }

    public List<User> getSortedUsers() {
        List<User> users =  new ArrayList<>();

        // gehe durch alle user und füge den chat in die queue ein
        Iterator it = this.users.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            User user = (User) pair.getValue();
            users.add(user);
        }

        // sortieren nach dem neusten Datum
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getName().compareTo(user2.getName());
            }
        });

        return users;
    }

    */

}
