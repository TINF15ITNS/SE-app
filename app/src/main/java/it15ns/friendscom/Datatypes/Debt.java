package it15ns.friendscom.Datatypes;

import java.util.Date;

import it15ns.friendscom.model.User;

/**
 * Created by valentin on 5/9/17.
 */

public class Debt {
    private Date returnDate;
    private double amount;
    private User debtor;
    private Date dateOfLoan;
    private User creditor;
    private boolean outstanding;

    public Debt(Date returnDate, double amount, User debtor, Date dateOfLoan, User creditor, boolean outstanding){
        this.returnDate = returnDate;
        this.amount = amount;
        this.debtor = debtor;
        this.dateOfLoan = dateOfLoan;
        this.creditor = creditor;
        this.outstanding = outstanding;
    }

    public void setReturnDate (Date returnDate){
        this.returnDate = returnDate;
    }
    public Date getReturnDate (){
        return this.returnDate;
    }
    public void setAmount (double amount){
        this.amount = amount;
    }
    public double getAmount (){
        return this.amount;
    }
    public void setUser (User debtor){
        this.debtor = debtor;
    }
    public User getDebtor (){
        return this.debtor;
    }
    public void setDateOfLoan (Date dateOfLoan){
        this.dateOfLoan = dateOfLoan;
    }
    public Date getDateOfLoan (){
        return this.dateOfLoan;
    }
    public void setCreditor (User creditor){
        this.creditor = creditor;
    }
    public User getCreditor (){
        return this.creditor;
    }
    public void setOutstanding (boolean outstanding){
        this.outstanding = outstanding;
    }
    public boolean getOutstanding (){
        return this.outstanding;
    }
}
