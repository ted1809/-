package com.example.logindb;

public class RawLogInfo {
    private String boxnum;
    private String goodsName;
    private boolean inOut;

    public RawLogInfo(){

    }

    public String getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(String boxnum) {
        this.boxnum = boxnum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public boolean isInOut() {
        return inOut;
    }

    public void setInOut(boolean inOut) {
        this.inOut = inOut;
    }
}
