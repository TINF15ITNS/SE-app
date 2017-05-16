package it15ns.friendscom.datatypes;

import java.util.Date;

import it15ns.friendscom.model.User;

/**
 * Created by valentin on 5/9/17.
 */

public class EventMessage extends ChatMessage {
    //private GoogleCalendarEntry calenderEntry;  //TODO: google calender verstehen
    //TODO: setter, getter, constructor

    public EventMessage(Date date, User sender){
        super(date, sender);
        //this.calenderEntry = calenderEntry;
    }
}
