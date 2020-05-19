package com.example.logindb;

import java.io.Serializable;

public class documentInfo implements Serializable {
    private String boxnum;
    private String userID;
    private String goodsName;
    private String detail;
    private boolean inOut;

    public documentInfo(){

    }

    public documentInfo(String boxnum, String userID, String goodsName, String detail, boolean inOut){
        this.boxnum = boxnum;
        this.userID = userID;
        this.goodsName = goodsName;
        this.detail = detail;
        this.inOut = inOut;
    }

    public String getBoxnum(){
        return this.boxnum;
    }
    public void setBoxnum(){
        this.boxnum = boxnum;
    }
    public String getUserID(){
        return this.userID;
    }
    public void setUserID(){
        this.userID = userID;
    }
    public String getGoodsName(){
        return this.goodsName;
    }
    public void setGoodsName(){
        this.goodsName = goodsName;
    }
    public String getDetail(){
        return this.detail;
    }
    public void setDetail(){
        this.detail = detail;
    }
    public Boolean getInOut(){
        return this.inOut;
    }
    public void setInOut(){
        this.inOut = inOut;
    }
}
