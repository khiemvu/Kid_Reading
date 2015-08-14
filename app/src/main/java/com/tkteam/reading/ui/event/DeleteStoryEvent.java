package com.tkteam.reading.ui.event;

import java.util.UUID;

/**
 * Created by Khiemvx on 8/2/2015.
 */
public class DeleteStoryEvent {
    private UUID storyId;

    public DeleteStoryEvent(UUID storyId) {
        this.storyId = storyId;
    }

    public UUID getStoryId() {
        return storyId;
    }
}
