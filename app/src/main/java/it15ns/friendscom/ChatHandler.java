package it15ns.friendscom;

import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.Datatypes.ChatMessage;
import it15ns.friendscom.model.User;

/**
 * Created by valentin on 5/9/17.
 */

public class ChatHandler {
    private List<Chat> chatList;

    public ChatHandler(){
        this.chatList = new ArrayList<Chat>();
    }

    public boolean chatExists(User friend){
        //TODO:
        return true;
    }
    public void createNewChat(User friend){
        //TODO:
    }
    public void showChat(Chat chat){
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
    public void saveMessageLocally(Chat affectedChat, ChatMessage message){
        //TODO:
    }
}
