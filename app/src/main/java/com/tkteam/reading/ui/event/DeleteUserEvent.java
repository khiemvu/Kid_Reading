package com.tkteam.reading.ui.event;

/**
 * Created by Khiemvx on 8/17/2015.
 */
public class DeleteUserEvent {

    private String userId;

    public DeleteUserEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
