package com.example.logindb;

public class memberinfo {
    private String name;
    private int boxnum;
    private String date;
    private boolean access;

    public memberinfo(String name, int boxnum, String date, boolean access){
        this.name = name;
        this.boxnum = boxnum;
        this.date = date;
        this.access = access;
    }

    public String getName(){
        return this.name;
    }
    public void setName(){
        this.name = name;
    }
    public int getBoxnum(){
        return this.boxnum;
    }
    public void setBoxnum(){
        this.boxnum = boxnum;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(){
        this.date = date;
    }
    public Boolean getAccess(){
        return this.access;
    }
    public void setAccess(){
        this.access = access;
    }
}
