package com.tkteam.reading.service;

import android.content.Context;
import android.database.Cursor;

import com.tkteam.reading.dao.StoryCreateDAO;
import com.tkteam.reading.dao.entites.StoryCreate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Khiemvx on 6/6/2015.
 */
public class StoryCreateService {
    public static StoryCreateService instance;
    private Context context;

    public StoryCreateService(Context context) {
        this.context = context;

    }

    public static StoryCreateService getInstance(Context context) {
        if (instance == null) {
            instance = new StoryCreateService(context);
        }
        return instance;
    }

    public StoryCreate create(StoryCreate storyCreate) throws SQLException {
        return StoryCreateDAO.getInstance(context).create(storyCreate);
    }

    public StoryCreate findById(UUID id) throws SQLException, IOException {
        return StoryCreateDAO.getInstance(context).findById(id);
    }

    public List<StoryCreate> findAll() throws SQLException {
        return StoryCreateDAO.getInstance(context).findAll();
    }

    public void update(StoryCreate profileEntity) throws SQLException {
        StoryCreateDAO.getInstance(context).update(profileEntity);
    }

    public void delete(StoryCreate storyCreate) throws SQLException {
        StoryCreateDAO.getInstance(context).delete(storyCreate);
    }

    public void createOrUpdate(StoryCreate storyCreate) throws SQLException {
        StoryCreateDAO.getInstance(context).createOrUpdate(storyCreate);
    }

    public StoryCreate convertDataToObject(Cursor c, StoryCreate storyCreate) {
        storyCreate.setId(UUID.fromString(c.getString(c.getColumnIndex(StoryCreate.ID))));
        storyCreate.setTitle(c.getString(c.getColumnIndex(StoryCreate.STORY_NAME)));
        storyCreate.setContent(c.getString(c.getColumnIndex(StoryCreate.STORY_CONTENT)));
        storyCreate.setThumb_image(c.getString(c.getColumnIndex(StoryCreate.IMAGE_URL)));
        storyCreate.setNumberQuestionAnswered(c.getString(c.getColumnIndex(StoryCreate.NUMBER_QUESTION_ANSWERED)));
        storyCreate.setNumberAnsweredCorrect(c.getString(c.getColumnIndex(StoryCreate.NUMBER_ANSWER_CORRECT)));
        return storyCreate;
    }
}
