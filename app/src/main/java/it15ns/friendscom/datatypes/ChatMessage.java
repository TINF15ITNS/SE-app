package it15ns.friendscom.datatypes;

import java.util.Date;

import it15ns.friendscom.model.User;

/**
 * Created by valentin on 5/9/17.
 */

public class ChatMessage {
    private Date date;
    private User sender;

    public ChatMessage(Date date, User sender){
        this.date = date;
        this.sender = sender;
    }


    public ChatMessage(Date date, String name){
        this.date = date;
        User user = new User();
        user.setName(name);
        this.sender = user;
    }

    public void setDate(Date date){
        this.date = date;
    }
    public Date getDate(){
        return this.date;
    }
    public void setSender(User sender){
        this.sender = sender;
    }
    public User getSender(){
        return this.sender;
    }
}
