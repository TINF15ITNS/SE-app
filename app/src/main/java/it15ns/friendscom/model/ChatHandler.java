package it15ns.friendscom.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danie on 15/05/2017.
 */

public class ChatHandler {

    private static ChatHandler instance;
    List<Chat> chats;

    private ChatHandler(){
        chats = new ArrayList<Chat>();
    }

    public static ChatHandler getInstance() {
        if(instance==null) {
            instance = new ChatHandler();
        }

        return instance;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public boolean addChat(Chat chat) {
        chats.add(chat);

        return true;
    }
}
