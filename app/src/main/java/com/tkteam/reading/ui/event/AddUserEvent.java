package com.tkteam.reading.ui.event;

/**
 * Created by Khiemvx on 8/17/2015.
 */
public class AddUserEvent {

    private String userId;

    public AddUserEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
