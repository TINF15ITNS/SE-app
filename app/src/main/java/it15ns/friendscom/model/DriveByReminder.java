package it15ns.friendscom.model;

import java.util.ArrayList;
import java.util.List;

import it15ns.friendscom.datatypes.Location;
import it15ns.friendscom.datatypes.UserLocation;

/**
 * Created by valentin on 5/9/17.
 */

public class DriveByReminder {
    private Location myLocation;
    private List<UserLocation> selectedLocations;
    private double reminderDistance;

    public DriveByReminder(Location myLocation, double reminderDistance){
        this.myLocation = myLocation;
        this.reminderDistance = reminderDistance;
        this.selectedLocations = new ArrayList<UserLocation>();
    }

    public void setMyLocation(Location myLocation){
        this.myLocation = myLocation;
    }
    public Location getMyLocation(){
        return this.myLocation;
    }
    public void setReminderDistance(double reminderDistance){
        this.reminderDistance = reminderDistance;
    }
    public double getReminderDistance(){
        return this.reminderDistance;
    }
}
