package com.tkteam.reading.ui.event;

/**
 * Created by Khiemvx on 8/17/2015.
 */
public class EditUserRespontEvent {
    private String userId;

    public EditUserRespontEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
