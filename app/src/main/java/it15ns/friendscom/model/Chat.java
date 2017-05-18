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
    XMPPClient xmppClient;
    String nickname;

    public Chat(String nickname) {
        messages = new LinkedList<ChatMessage>() ;
        xmppClient = XMPPClient.getInstance();
        this.nickname = nickname;
    }

    public void sendTextMessage(TextMessage textMessage){
        xmppClient.sendMsg(nickname, textMessage.getMessage());
        messages.add(textMessage);
    }

    public Date getNewestDate() {
        return messages.peek().getDate();
    }

    // wird vom XMPPChatListener aufgerufen
    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    // Für die initialisierung
    public void setMessages(Queue messages) {
        this.messages = messages;
    }

    public String getName() {
        return nickname;
    }

    public Queue<ChatMessage> getMessages() {
        return messages;
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
