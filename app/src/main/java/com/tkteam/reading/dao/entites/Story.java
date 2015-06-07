package com.tkteam.reading.dao.entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Khiemvx on 6/6/2015.
 */
@DatabaseTable(tableName = Story.TABLE_NAME)
public class Story extends Base {
    public static final String TABLE_NAME = "story";
    public static final String STORY_NAME = "storyName";
    public static final String STORY_CONTENT = "storyContent";
    public static final String IMAGE_URL = "imageUrl";

    @DatabaseField
    private String storyName;
    @DatabaseField
    private String storyContent;
    @DatabaseField
    private String imageUrl;

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getStoryContent() {
        return storyContent;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
