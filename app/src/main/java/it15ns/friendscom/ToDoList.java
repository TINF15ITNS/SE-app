package it15ns.friendscom;

import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.Datatypes.TodoListEntry;

/**
 * Created by valentin on 5/9/17.
 */

public class ToDoList {
    private List<TodoListEntry> entries;
    private String name;
    private User creator;

    public ToDoList(String name, User creator){
        this.name = name;
        this.creator = creator;
        this.entries = new ArrayList<TodoListEntry>();
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setCreator(User creator){
        this.creator = creator;
    }
    public User getCreator(){
        return this.creator;
    }
}
