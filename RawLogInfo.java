package com.example.login;

public class RawLogInfo {
    private String ID;
    private String boxnum;
    private boolean inOut;

    public RawLogInfo(){

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getBoxnum() {
        return boxnum;
    }

    public void setBoxnum(String boxnum) {
        this.boxnum = boxnum;
    }

    public boolean isInOut() {
        return inOut;
    }

    public void setInOut(boolean inOut) {
        this.inOut = inOut;
    }
}
