package it15ns.friendscom.model;

import java.util.List;

import it15ns.friendscom.datatypes.Debt;

/**
 * Created by danie on 16/05/2017.
 */

public class Group {

    private List<User> participants;
    private List<ToDoList> toDoLists;
    private List<Debt> debts;

    private Chat chat;
    //private Calendar calendar;

    public Group() {

    }

    public void createChat() {
        chat = new Chat("kk");
    }

    public List<User> getParticipants() {
        return participants;
    }

    public Chat getChat() {
        return chat;
    }
}
