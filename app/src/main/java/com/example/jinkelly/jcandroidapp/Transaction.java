package com.example.jinkelly.jcandroidapp;

/**
 * Created by jin.chung on 19/2/2016.
 */
public class Transaction {
    private String Action;
    private String Timestamp;
    private Box Box;
    private Location Loc;

    public Transaction(String action, String timestamp, com.example.jinkelly.jcandroidapp.Box box, Location loc) {
        Action = action;
        Timestamp = timestamp;
        Box = box;
        Loc = loc;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(String timestamp) {
        Timestamp = timestamp;
    }

    public com.example.jinkelly.jcandroidapp.Box getBox() {
        return Box;
    }

    public void setBox(com.example.jinkelly.jcandroidapp.Box box) {
        Box = box;
    }

    public Location getLoc() {
        return Loc;
    }

    public void setLoc(Location loc) {
        Loc = loc;
    }
}
