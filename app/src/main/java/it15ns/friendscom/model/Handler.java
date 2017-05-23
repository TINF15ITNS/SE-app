package it15ns.friendscom.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListResourceBundle;
import java.util.Queue;

import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.fragments.SpecificChatFragment;

/**
 * Created by danie on 15/05/2017.
 */

public class Handler {

    private static Handler instance;
    List<Group> groups;
    HashMap<String, User> users;
    User me;

    private Handler(){
        me = getMe();
        groups = new ArrayList<Group>();
        users = new HashMap<String, User>();
    }

    public static Handler getInstance() {
        if(instance==null) {
            Log.d("waas","geeeeht");
            instance = new Handler();
        }
        return instance;
    }

    public User getMe() {
        //TODO: grpc call
        return new User("Me");
    }

    public void addGroup(Group group) {
        groups.add(group);
    }

    public void removeGroup(Group group) {
        groups.remove(group);
    }

    public void addUser(User user) {
        users.put(user.getNickname(), user);
    }

    public void removeUser(User user) {
        if(users.containsKey(user.getNickname()))
            users.remove(user.getNickname());
    }

    public User getUser(String nickname) {
        if(users.containsKey(nickname)) {
            return users.get(nickname);
        } else {
            User user = new User(nickname);
            users.put(nickname, user);
            return user;
        }
    }

    public boolean groupExistsForUser(User user) {
        for(Object groupObject : groups) {
            Group group = (Group) groupObject;
            List<User> users = group.getParticipants();
            for(Object userObject : users) {
                User participant = (User) userObject;
                if(participant.getNickname().equals(user.getNickname())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<User> getUser() {
        List<User> users =  new ArrayList<>();

        // gehe durch alle user und füge den chat in die queue ein
        Iterator it = this.users.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            User user = (User) pair.getValue();
            users.add(user);
        }

        // sortieren nach dem neusten Datum
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User user1, User user2) {
                return user1.getName().compareTo(user2.getName());
            }
        });

        return users;
    }
    // gibt eine Liste aller Chats, dem neustem Daten einer nachricht nach sortiert zurück
    //
    public List<Chat> getChats() {
        List<Chat> chats =  new ArrayList<>();

        // gehe durch alle user und füge den chat in die queue ein
        Iterator it = users.entrySet().iterator();
        while (it.hasNext()) {
            HashMap.Entry pair = (HashMap.Entry)it.next();
            User user = (User) pair.getValue();
            if(user.hasChat())
                chats.add(user.getChat());
        }

        for(Group group: groups) {
            if(group.hasChat())
                chats.add(group.getChat());
        }

        // sortieren nach dem neusten Datum
        chats.sort(new Comparator<Chat>() {
            @Override
            public int compare(Chat chat1, Chat chat2) {
                return chat1.getNewestDate().compareTo(chat2.getNewestDate());
            }
        });

        return chats;
    }

    // MEthode für die Specific Chat Activity. Liste der Chats -> nur nickname verfügbar und keine gedanken über user
    public Chat getUserChat(String nickname) {
        User user = getUser(nickname);
        if(user.hasChat())
            return user.getChat();
        else
            return user.createChat();
    }

    public void showChat(User user){
        //TODO:
    }

    public void deleteChat(User friend){
        //TODO:
    }

    public void deleteChatMessages(User friend){
        //TODO:
    }

    public void notifyUserAboutMessage(ChatMessage chatMessage){
        //TODO:
    }

    public void saveMessageLocally(ChatMessage message){
        //TODO:
    }

        /*
    private boolean chatExists(String friendsName){
        for(Object chatObject: chats) {
            Chat chat = (Chat) chatObject;
            if(chat.getName().equals(friendsName)) {
                return true;
            }
        }
        return false;
    }

    private void createNewChat(User friend){
        Chat chat = new Chat();
        chats.add(chat);
    }

    private Chat createNewChat(String friendsname){
        Chat chat = new Chat();
        chat.setName(friendsname);
        chats.add(chat);
        return chat;
    }

    public Chat getChat(String friendsName) {
        Chat retChat = null;
        for(Object chatObject: chats) {
            Chat chat = (Chat) chatObject;
            if(chat.getName().equals(friendsName)) {
                retChat = chat;
            }
        }          return retChat;
    }

    */
}
