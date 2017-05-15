package it15ns.friendscom;

import android.media.Image;

/**
 * Created by valentin on 5/9/17.
 */

public class GroupChat extends Chat {
    private Image groupPicture;
    private String name;

    public GroupChat(Calendar chatCalendar, Image groupPicture, String name){
        super(chatCalendar);
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
