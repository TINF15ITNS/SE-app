package it15ns.friendscom.datatypes;

/**
 * Created by valentin on 5/9/17.
 */

public class TodoListEntry {
    private String content;
    private boolean completed;

    public TodoListEntry(String content, boolean completed){
        this.content = content;
        this.completed = completed;
    }

    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setCompleted(boolean completed){
        this.completed = completed;
    }
    public boolean getCompleted(){
        return this.completed;
    }
}
