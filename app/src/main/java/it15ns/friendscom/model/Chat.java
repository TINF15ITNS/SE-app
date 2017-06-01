package it15ns.friendscom.model;

import android.content.Context;
import android.provider.CalendarContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.Location;
import it15ns.friendscom.datatypes.TextMessage;
import it15ns.friendscom.handler.SQLiteHandler;
import it15ns.friendscom.xmpp.XMPPClient;

/**
 * Created by danie on 15/05/2017.
 */

public class Chat {
    private LinkedList<ChatMessage> messages;
    SQLiteHandler sqLiteHandler;
    String nickname;

    public Chat(String nickname, Context context) {
        messages = new LinkedList<ChatMessage>();
        sqLiteHandler = new SQLiteHandler(context);
        try {
            if(!sqLiteHandler.isTableExists(nickname))
                sqLiteHandler.createChatTable(nickname);
            else
                messages = sqLiteHandler.getAllMessages(nickname, context);
            this.nickname = nickname;
        } catch (RuntimeException ex) {
            Log.d("SqlLite", ex.toString());
        }
    }

    public void sendTextMessage(TextMessage textMessage, Context context){
        sqLiteHandler.addMessageToDB(textMessage, this);

        messages.add(textMessage);
        XMPPClient.sendMsg(nickname, textMessage.getMessage());
    }

    public Date getNewestDate() {
        if(messages.size()>0)
            return messages.getLast().getDate();
        else
            return new Date(0);
    }

    public ChatMessage getNewestMessage() {
        if(messages.size() > 0)
            return messages.getLast();
        else return null;
    }

    // wird vom XMPPChatListener aufgerufen
    public void addMessage(ChatMessage message) {
        sqLiteHandler.addMessageToDB(message, this);
        messages.add(message);
    }

    // Für die initialisierung
    public void setMessages(LinkedList messages) {
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
