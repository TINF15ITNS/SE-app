package it15ns.friendscom.model;

import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.datatypes.ChatMessage;
import it15ns.friendscom.datatypes.Location;

/**
 * Created by danie on 15/05/2017.
 */

public class Chat {

    String name;

    private List<User> participants;
    private List<ChatMessage> messages;
    private List<ToDoList> toDoLists;
    private Calendar chatCalendar;
    private int position;

    public Chat() {
        messages = new ArrayList<ChatMessage>() ;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessages(List messages) {
        this.messages = messages;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
    }

    public String getName() {

        return name;
    }

     /*
     public SpecificChatFragment(Calendar chatCalendar){
        this.chatCalendar = chatCalendar;
        this.participants = new ArrayList<User>();
        this.messages = new ArrayList<ChatMessage>();
        this.toDoLists = new ArrayList<ToDoList>();
    }
    */

    public void setChatCalendar(Calendar chatCalendar){
        this.chatCalendar = chatCalendar;
    }

    public Calendar getChatCalendar(){
        return this.chatCalendar;
    }

    public void createTextMessage(String stringOfTextBox){
        //TODO:
    }
    public void createTodoListMessage(ToDoList toDoList){
        //TODO:
    }
    public void createSharLocationMessage(Location location){
        //TODO:
    }
    //public void createEventMessage(GoogleCalendarEntry calendarEntry){
    //TODO:
    //}

    public List getMessages() {
        return messages;
    }
}
