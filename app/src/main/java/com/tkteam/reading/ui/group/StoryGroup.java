package com.tkteam.reading.ui.group;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tkteam.reading.R;
import com.tkteam.reading.dao.entites.Story;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Khiemvx on 6/6/2015.
 */
public class StoryGroup extends Group {

    private String storyName;
    private String description;
    private String storyUrl;

    public static List<StoryGroup> convertFromStory(List<Story> storyList) {
        List<StoryGroup> storyGroups = new ArrayList<>();
        for (Story story : storyList) {
            StoryGroup userGroup = new StoryGroup();
            userGroup.setStoryName(story.getStoryName());
            userGroup.setDescription(story.getStoryContent());
            userGroup.setStoryUrl(story.getImageUrl());
            storyGroups.add(userGroup);
        }
        return storyGroups;
    }

    @Override
    public int getLayout() {
        return R.layout.list_view_story_item;
    }

    @Override
    public void findById(ViewHolder viewHolder, View view) {
        ImageView ivStory = (ImageView) view.findViewById(R.id.ivStory);
        TextView tvStoryName = (TextView) view.findViewById(R.id.tvStoryName);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        viewHolder.addView(ivStory);
        viewHolder.addView(tvStoryName);
        viewHolder.addView(tvDescription);
    }

    @Override
    public void setDataToView(ViewHolder viewHolder, View view) {
        ImageView ivStory = (ImageView) viewHolder.getView(R.id.ivStory);
        TextView tvStoryName = (TextView) viewHolder.getView(R.id.tvStoryName);
        TextView tvDescription = (TextView) viewHolder.getView(R.id.tvDescription);


    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }
}
