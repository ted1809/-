package com.example.logindb;

public class memberinfo {
    private String name;
    private String phone;
    private String date;
    private String address;

    public memberinfo(String name, String phone, String date, String address){
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.address = address;
    }

    public String getName(){
        return this.name;
    }
    public void setName(){
        this.name = name;
    }
    public String getPhone(){
        return this.phone;
    }
    public void setPhone(){
        this.phone = phone;
    }
    public String getDate(){
        return this.date;
    }
    public void setDate(){
        this.date = date;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(){
        this.address = address;
    }
}
