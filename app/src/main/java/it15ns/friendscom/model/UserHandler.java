package it15ns.friendscom.model;

/**
 * Created by valentin on 5/9/17.
 */

public class UserHandler {
    private User localUser;

    public UserHandler(User localUser){
        this.localUser = localUser;
    }

    public void setLocalUser(User localUser){
        this.localUser = localUser;
    }
    public User getLocalUser(){
        return this.localUser;
    }
}
