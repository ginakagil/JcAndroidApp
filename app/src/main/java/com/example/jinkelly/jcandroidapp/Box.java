package com.example.jinkelly.jcandroidapp;

/**
 * Created by jin.chung on 19/2/2016.
 */

public class Box {
    private String Dept;
    private String Boxno;
    private String Slotid;
    private String Slottxt;

    public Box(String dept, String boxno, String slotid, String slottxt) {
        Dept = dept;
        Boxno = boxno;
        Slotid = slotid;
        Slottxt = slottxt;
    }

    public String getDept() {
        return Dept;
    }

    public void setDept(String dept) {
        Dept = dept;
    }

    public String getBoxno() {
        return Boxno;
    }

    public void setBoxno(String boxno) {
        Boxno = boxno;
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

