package it15ns.friendscom.model;

import android.media.Image;

import java.util.Date;
import java.util.List;

import it15ns.friendscom.datatypes.Debt;
import it15ns.friendscom.datatypes.Location;

/**
 * Created by valentin on 5/9/17.
 */

public class User {

    private String nickname;
    private String name;
    private String surname;
    private Date birthday;
    private String telNumber;
    private String eMail;
    private Image profilePicture;
    private double totalDebtsOut;
    private double totalDebtsIn;
    private Location myLocation;
    private byte shareLocation;

    private List<Debt> debts;
    private Chat chat;
    private List<ToDoList> toDoLists;
    //private Calender calender;

    public User(){

    }

    public User(String nickname) {
        setNickname(nickname);
    }

    public Chat getChat() {
        return chat;
    }

    public boolean hasChat() {
        return chat != null;
    }

    public Chat createChat(){
        chat = new Chat(nickname);
        return chat;
    }

    // Setter für Variablen
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public void setProfilePicture(Image profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setDebts(List<Debt> debts) {
        this.debts = debts;
    }

    public void setTotalDebtsOut(double totalDebtsOut) {
        this.totalDebtsOut = totalDebtsOut;
    }

    public void setTotalDebtsIn(double totalDebtsIn) {
        this.totalDebtsIn = totalDebtsIn;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    public void setShareLocation(byte shareLocation) {
        this.shareLocation = shareLocation;
    }

    public void setToDoLists(List<ToDoList> toDoLists) {
        this.toDoLists = toDoLists;
    }




    // Getter für Variablen
    public String getNickname() { return nickname;  }

    public String getName() {
        return name != null ? name : nickname;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public String geteMail() {
        return eMail;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public List<Debt> getDebts() {
        return debts;
    }

    public double getTotalDebtsOut() {
        return totalDebtsOut;
    }

    public double getTotalDebtsIn() {
        return totalDebtsIn;
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public byte getShareLocation() {
        return shareLocation;
    }

    public List<ToDoList> getToDoLists() {
        return toDoLists;
    }
}
