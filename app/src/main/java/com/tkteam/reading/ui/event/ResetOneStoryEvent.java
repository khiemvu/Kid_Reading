package com.tkteam.reading.ui.event;

/**
 * Created by Khiemvx on 8/16/2015.
 */
public class ResetOneStoryEvent {
    private String storyId;
    private boolean isCreateStory;

    public ResetOneStoryEvent(String storyId, boolean isCreateStory) {
        this.storyId = storyId;
        this.isCreateStory = isCreateStory;
    }

    public String getStoryId() {
        return storyId;
    }

    public boolean isCreateStory() {
        return isCreateStory;
    }
}
