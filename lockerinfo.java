package com.example.logindb;

public class lockerinfo {
    private String boxID;
    private String userID;

    public lockerinfo(String boxID,String userID){
        this.boxID = boxID;
        this.userID = userID;
    }

    public String getBoxID(){
        return this.boxID;
    }
    public void setBoxID(){
        this.boxID = boxID;
    }
    public String getUserID(){
        return this.userID;
    }
    public void setUserID(){
        this.userID = userID;
    }

}
