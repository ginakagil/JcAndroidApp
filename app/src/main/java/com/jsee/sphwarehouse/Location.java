package com.jsee.sphwarehouse;

/**
 * Created by jin.chung on 19/2/2016.
 */
public class Location {
    private String Slotid;
    private String Slottxt;

    public Location( String slotid, String slottxt) {
        Slottxt = slottxt;
        Slotid = slotid;
    }

    public String getSlotid() {
        return Slotid;
    }

    public void setSlotid(String slotid) {
        Slotid = slotid;
    }

    public String getSlottxt() {
        return Slottxt;
    }

    public void setSlottxt(String slottxt) {
        Slottxt = slottxt;
    }
}
