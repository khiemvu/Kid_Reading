package com.tkteam.reading.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tkteam.reading.dao.entites.Story;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Khiemvx on 6/20/2015.
 */
public class StoryDAO {
    private static StoryDAO instance;
    Dao<Story, String> storyDAO;

    public StoryDAO(Context context) throws SQLException {
        this.storyDAO = DatabaseHelper.getInstance(context).getStoryDao();
    }

    public static StoryDAO getInstance(Context context) throws SQLException {
        if (instance == null) {
            instance = new StoryDAO(context);
        }
        return instance;
    }

    public Story create(Story story) throws SQLException {
        return storyDAO.createIfNotExists(story);
    }

    public void update(Story story) throws SQLException {
        storyDAO.update(story);
    }

    public Story findById(String id) throws SQLException, IOException {
        return storyDAO.queryForId(id);
    }

    public List<Story> findAll() throws SQLException {
        return storyDAO.queryForAll();
    }

    public void createOrUpdate(Story story) throws SQLException {
        storyDAO.createOrUpdate(story);
    }

    public void delete(Story story) throws SQLException {
        storyDAO.delete(story);
    }
}
