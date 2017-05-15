package it15ns.friendscom;

import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.Datatypes.Location;
import it15ns.friendscom.Datatypes.UserLocation;

/**
 * Created by valentin on 5/9/17.
 */

public class Map {
    private Location myLocation;
    private List<UserLocation> sharedLocations;
    //private GoogleMap googleMap;  //TODO: google map verstehen

    public Map(Location myLocation){
        this.myLocation = myLocation;
        this.sharedLocations = new ArrayList<UserLocation>();
    }

    public void setMyLocation(Location myLocation){
        this.myLocation = myLocation;
    }
    public Location getMyLocation(){
        return this.myLocation;
    }
}
