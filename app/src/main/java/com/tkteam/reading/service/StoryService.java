package com.tkteam.reading.service;

import android.content.Context;
import android.database.Cursor;

import com.tkteam.reading.dao.StoryDAO;
import com.tkteam.reading.dao.entites.Story;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Khiemvx on 6/20/2015.
 */
public class StoryService {
    public static StoryService instance;
    private Context context;

    public StoryService(Context context) {
        this.context = context;

    }

    public static StoryService getInstance(Context context) {
        if (instance == null) {
            instance = new StoryService(context);
        }
        return instance;
    }

    public Story create(Story story) throws SQLException {
        return StoryDAO.getInstance(context).create(story);
    }

    public Story findById(String id) throws SQLException, IOException {
        return StoryDAO.getInstance(context).findById(id);
    }

    public List<Story> findAll() throws SQLException {
        return StoryDAO.getInstance(context).findAll();
    }

    public void update(Story profileEntity) throws SQLException {
        StoryDAO.getInstance(context).update(profileEntity);
    }

    public void delete(Story story) throws SQLException {
        StoryDAO.getInstance(context).delete(story);
    }

    public void createOrUpdate(Story story) throws SQLException {
        StoryDAO.getInstance(context).createOrUpdate(story);
    }

    public Story convertDataToObject(Cursor c, Story story) {
        story.setId(c.getString(c.getColumnIndex(Story.ID)));
        story.setTitle(c.getString(c.getColumnIndex(Story.STORY_NAME)));
        story.setContent(c.getString(c.getColumnIndex(Story.STORY_CONTENT)));
        story.setThumb_image(c.getString(c.getColumnIndex(Story.IMAGE_URL)));
        return story;
    }
}
