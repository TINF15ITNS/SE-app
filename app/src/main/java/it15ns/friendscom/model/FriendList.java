package it15ns.friendscom.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin on 5/9/17.
 */

public class FriendList {
    private static FriendList instance;
    private List<User> friends;

    private FriendList(){
        this.friends = new ArrayList<User>();
    }

    public static FriendList getInstance()  {
        if(instance == null) {
            instance = new FriendList();
        }

        return instance;
    }

    public User getUserforNickname(String nickname, Context context) {
        for(User friend : friends) {
            if(friend.getNickname() == nickname)
                return friend;
        }

        User user = new User(nickname);
        user.setNickname(nickname);

        addUserToFriendList(user);

        return user;
    }

    public void addUserToFriendList(User user) {
        friends.add(user);
    }
}
