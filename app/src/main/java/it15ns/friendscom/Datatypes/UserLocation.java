package it15ns.friendscom.Datatypes;

import it15ns.friendscom.Datatypes.Location;

/**
 * Created by valentin on 5/9/17.
 */

public class UserLocation {
    private String userNickname;
    private Location location;

    public UserLocation(String userNickname, Location location){
        this.userNickname = userNickname;
        this.location = location;
    }

    public void setUserNickname(String userNickname){
        this.userNickname = userNickname;
    }
    public String getUserNickname(){
        return this.userNickname;
    }
    public void setLocation(Location location){
        this.location = location;
    }
    public Location getLocation(){
        return this.location;
    }
}
