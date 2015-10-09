package com.tkteam.reading.ui.event;

/**
 * Created by Khiemvx on 8/16/2015.
 */
public class ResetAllStoryEvent {

    private boolean isCreateStory;

    public ResetAllStoryEvent(boolean isCreateStory) {
        this.isCreateStory = isCreateStory;
    }

    public boolean isCreateStory() {
        return isCreateStory;
    }
}
