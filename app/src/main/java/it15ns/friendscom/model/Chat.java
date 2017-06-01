package it15ns.friendscom.model;

import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.Location;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.xmpp.XMPPClient;

/**
 * Created by danie on 15/05/2017.
 */

public class Chat {
    private Queue<ChatMessage> messages;
    String nickname;

    public Chat(String nickname) {
        messages = new LinkedList<ChatMessage>() ;
        this.nickname = nickname;
    }

    public void sendTextMessage(TextMessage textMessage){
        messages.add(textMessage);
        XMPPClient.sendMsg(nickname, textMessage.getMessage());
    }

    public Date getNewestDate() {
        return messages.peek().getDate();
    }

    public ChatMessage getNewestMessage() {
        return messages.peek();
    }

    // wird vom XMPPChatListener aufgerufen
    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    // Für die initialisierung
    public void setMessages(Queue messages) {
        this.messages = messages;
    }

    public String getNickname() {
        return nickname;
    }

    public Queue<ChatMessage> getMessages() {
        return messages;
    }
    public List<ChatMessage> getMessagesList() {
        List<ChatMessage> list;
        list = new ArrayList<>(messages);

        return list;
    }
    /*
    public void createTodoListMessage(ToDoList toDoList){
        //TODO:
    }

    public void createSharLocationMessage(Location location){
        //TODO:
    }
    */
}
