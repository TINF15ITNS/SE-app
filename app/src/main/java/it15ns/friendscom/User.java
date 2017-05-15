package it15ns.friendscom;

import android.media.Image;

import java.util.Date;
import java.util.List;

import it15ns.friendscom.Datatypes.Debt;
import it15ns.friendscom.Datatypes.Location;

/**
 * Created by valentin on 5/9/17.
 */

public class User {

    private String nickname;
    private String password;
    private String name;
    private String surname;
    private Date birthday;
    private List<FriendList> friends;
    private ChatHandler userChatHandler;
    private String telNumber;
    private String eMail;
    private Image profilePicture;
    private List<Debt> debts;
    private double totalDebtsOut;
    private double totalDebtsIn;
    private Location myLocation;
    private byte shareLocation;
    //private Calender calender;    //TODO: google calender verstehen
    private List<ToDoList> toDoLists;

    public User(){

    }
}
