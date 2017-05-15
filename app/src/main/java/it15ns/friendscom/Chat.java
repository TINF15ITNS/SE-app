package it15ns.friendscom;

import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.Datatypes.ChatMessage;
import it15ns.friendscom.Datatypes.Location;

/**
 * Created by valentin on 5/9/17.
 */

public class Chat {
    private List<User> participants;
    private List<ChatMessage> messages;
    private List<ToDoList> toDoLists;
    private Calendar chatCalendar;

    public Chat(Calendar chatCalendar){
        this.chatCalendar = chatCalendar;
        this.participants = new ArrayList<User>();
        this.messages = new ArrayList<ChatMessage>();
        this.toDoLists = new ArrayList<ToDoList>();
    }

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
    public void createEventMessage(GoogleCalendarEntry calendarEntry){
        //TODO:
    }
    public boolean isTextBoxEmpty(){
        //TODO:
        return true;
    }
    public void sendMessage(ChatMessage message){
        //TODO:
    }
    public void clearTextbox(){
        //TODO:
    }
}
