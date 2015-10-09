package com.tkteam.reading.ui.event;

import java.util.UUID;

/**
 * Created by Khiemvx on 8/23/2015.
 */
public class SetActiveUserEvent {
    private UUID userId;

    public SetActiveUserEvent(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }
}
