package it15ns.friendscom.Datatypes;

import java.util.Date;

/**
 * Created by valentin on 5/9/17.
 */

public class Location {
    private float longitude;
    private float latitude;
    private Date timestamp;

    public Location(float longitude, float latitude, Date timestamp){
        this.longitude = longitude;
        this.latitude = latitude;
        this.timestamp = timestamp;
    }

    public void setLongitude(float longitude){
        this.longitude = longitude;
    }
    public float getLongitude(){
        return this.longitude;
    }
    public void setLatitude(float latitude){
        this.latitude = latitude;
    }
    public float getLatitude(){
        return this.latitude;
    }
    public void setTimestamp(Date timestamp){
        this.timestamp = timestamp;
    }
    public Date getTimestamp(){
        return this.timestamp;
    }

}
