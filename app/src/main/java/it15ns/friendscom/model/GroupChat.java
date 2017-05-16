package it15ns.friendscom.model;

import android.media.Image;

import it15ns.friendscom.fragments.SpecificChatFragment;

/**
 * Created by valentin on 5/9/17.
 */

public class GroupChat extends Chat {
    private Image groupPicture;
    private String name;

    public GroupChat(Calendar chatCalendar, Image groupPicture, String name){

        this.groupPicture = groupPicture;
        this.name = name;
    }

    public void setGroupPicture(Image groupPicture){
        this.groupPicture = groupPicture;
    }
    public Image getGroupPicture(){
        return this.groupPicture;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
