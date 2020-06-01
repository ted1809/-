package com.example.login;

import java.io.Serializable;

public class memberinfo implements Serializable {
    private String name;
    private String boxnum;
    private String date;
    private String phoneNumber;
    private boolean access;



    public memberinfo(){
    }

    public memberinfo(memberinfo memberinfo){
        this.name = memberinfo.getName();
        this.boxnum = memberinfo.getBoxnum();
        this.date = memberinfo.getDate();
        this.phoneNumber = memberinfo.getPhoneNumber();
        this.access = memberinfo.getAccess();
    }

    public memberinfo(String name,String phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public memberinfo(String name, String boxnum, String date,String phoneNumber, boolean access){
        this.name = name;
        this.boxnum = boxnum;
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.access = access;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getBoxnum(){
        return this.boxnum;
    }
    public void setBoxnum(String boxnum){
        this.boxnum = boxnum;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(String date){
        this.date = date;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Boolean getAccess(){
        return this.access;
    }
    public void setAccess(boolean access){
        this.access = access;
    }
}
