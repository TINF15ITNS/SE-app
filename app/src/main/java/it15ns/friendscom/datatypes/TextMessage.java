package it15ns.friendscom.datatypes;

import java.util.Date;

import it15ns.friendscom.model.User;

/**
 * Created by valentin on 5/9/17.
 */

public class TextMessage extends ChatMessage {
    private String message;

    public TextMessage(Date date, User sender, String message){
        super(date, sender);
        this.message = message;
    }

    public TextMessage(Date date, String senderName, String message){
        super(date, senderName);
        this.message = message;
    }

    public TextMessage(String message){
        super(new Date(), new User());
        this.message = message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
