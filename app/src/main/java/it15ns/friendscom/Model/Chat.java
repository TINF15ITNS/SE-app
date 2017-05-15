package it15ns.friendscom.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by danie on 15/05/2017.
 */

public class Chat {
    String name;
    List<String>  messages;

    public Chat() {
        messages = new ArrayList<String>() ;

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMessages(List messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public String getName() {

        return name;
    }

    public List getMessages() {
        return messages;
    }
}
