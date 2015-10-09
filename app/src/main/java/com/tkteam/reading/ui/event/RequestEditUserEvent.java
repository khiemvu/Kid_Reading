package com.tkteam.reading.ui.event;

/**
 * Created by Khiemvx on 8/17/2015.
 */
public class RequestEditUserEvent {
    private String userId;

    public RequestEditUserEvent(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
