package myPackage;
import java.util.*;

public class transaction {
	// Private and Public Variables
    private String senderID_;
    private double amount_;
    private String recieverID_;

    // Constructor
    transaction (String senderID, double amount, String recieverID) {
        this.senderID_ = senderID;
        this.amount_ = amount;
        this.recieverID_ = recieverID;
    }

    transaction (String senderID, double amount) {
        this.senderID_ = senderID;
        this.amount_ = amount;
        this.recieverID_ = "Sell";
    }

    // Getters
    public String getSenderID(){
        return senderID_;
    }
    public double getAmount(){
        return amount_;
    }
    public String getRecieverID(){
        return recieverID_;
    }
    @Override
    public String toString(){
        return "Sender ID: " + senderID_
        + "\nAmount: " + amount_.toString()
        + "\nReciever ID: " + recieverID_;
    }
}
