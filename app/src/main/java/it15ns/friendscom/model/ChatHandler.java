package it15ns.friendscom.model;

import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.fragments.SpecificChatFragment;

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

    public boolean chatExists(User friend){
        //TODO:
        return true;
    }

    public boolean chatExists(String friendsName){
        for(Object chatObject: chats) {
            Chat chat = (Chat) chatObject;
            if(chat.getName().equals(friendsName)) {
                return true;
            }
        }
        return false;
    }

    public void createNewChat(User friend){
        Chat chat = new Chat();
        chats.add(chat);
    }

    public Chat createNewChat(String friendsname){
        Chat chat = new Chat();
        chat.setName(friendsname);
        chats.add(chat);
        return chat;
    }

    public Chat getChat(int position) {
        return chats.get(position);
    }

    public Chat getChat(String friendsName) {
        Chat retChat = null;
        for(Object chatObject: chats) {
            Chat chat = (Chat) chatObject;
            if(chat.getName().equals(friendsName)) {
                retChat = chat;
            }
        }

        return retChat;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void showChat(SpecificChatFragment chat){
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
    public void saveMessageLocally(SpecificChatFragment affectedChat, ChatMessage message){
        //TODO:
    }
}
