package com.example.logindb;

import java.io.Serializable;

public class memberinfo implements Serializable {
    private String name;
    private String boxnum;
    private String date;
    private boolean access;

    public memberinfo(){
    }

    public memberinfo(memberinfo memberinfo){
        this.name = memberinfo.getName();
        this.boxnum = memberinfo.getBoxnum();
        this.date = memberinfo.getDate();
        this.access = memberinfo.getAccess();
    }

    public memberinfo(String name, String boxnum, String date, boolean access){
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
    public String getBoxnum(){
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
