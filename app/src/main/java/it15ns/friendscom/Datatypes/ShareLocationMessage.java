package it15ns.friendscom.Datatypes;

import it15ns.friendscom.*;
import java.util.Date;

/**
 * Created by valentin on 5/9/17.
 */

public class ShareLocationMessage extends ChatMessage {
    private UserLocation userLocation;

    public ShareLocationMessage(Date date, User sender, UserLocation userLocation){
        super(date, sender);
        this.userLocation = userLocation;
    }

    public void setUserLocation(UserLocation userLocation){
        this.userLocation = userLocation;
    }
    public UserLocation getUserLocation(){
        return this.userLocation;
    }

}
