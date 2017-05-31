package it15ns.friendscom.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by danie on 31/05/2017.
 */

public class ChatHandler {
    private static ChatHandler instance;

    private ChatHandler() {}

    // Methoden für das Chat Handling

    // MEthode für die Specific Chat Activity. Liste der Chats -> nur nickname verfügbar und keine gedanken über user
    public Chat getChat(String nickname) {
        User user = UserHandler.getUser(nickname);
        if(user.hasChat())
            return user.getChat();
        else
            return user.createChat();
    }

    // gibt eine Liste aller Chats, dem neustem Daten einer nachricht nach sortiert zurück
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
        Collections.sort(chats, new Comparator<Chat>() {
            @Override
            public int compare(Chat chat1, Chat chat2) {
                return chat1.getNewestDate().compareTo(chat2.getNewestDate());
            }
        });

        return chats;
    }

    public void deleteChatMessages(User friend){
        //TODO:
    }

    public void deleteChat(User friend){
        //TODO:
    }

    public void saveMessagesLocally(){
        //TODO:
    }

}
