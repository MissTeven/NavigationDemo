package com.oaksdance.navigationdemo.event;

public class CheckEvent {
    private int checkId;

    public CheckEvent(int id) {
        this.checkId = id;
    }

    public int getCheckId() {
        return checkId;
    }
}
