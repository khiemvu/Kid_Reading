package com.tkteam.reading.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tkteam.reading.dao.entites.StoryCreate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Khiemvx on 6/6/2015.
 */
public class StoryCreateDAO {
    private static StoryCreateDAO instance;
    Dao<StoryCreate, UUID> storyDAO;

    public StoryCreateDAO(Context context) throws SQLException {
        this.storyDAO = DatabaseHelper.getInstance(context).getStoryCreateDao();
    }

    public static StoryCreateDAO getInstance(Context context) throws SQLException {
        if (instance == null) {
            instance = new StoryCreateDAO(context);
        }
        return instance;
    }

    public StoryCreate create(StoryCreate storyCreate) throws SQLException {
        return storyDAO.createIfNotExists(storyCreate);
    }

    public void update(StoryCreate storyCreate) throws SQLException {
        storyDAO.update(storyCreate);
    }

    public StoryCreate findById(UUID id) throws SQLException, IOException {
        return storyDAO.queryForId(id);
    }

    public List<StoryCreate> findAll() throws SQLException {
        return storyDAO.queryForAll();
    }

    public void createOrUpdate(StoryCreate storyCreate) throws SQLException {
        storyDAO.createOrUpdate(storyCreate);
    }

    public void delete(StoryCreate storyCreate) throws SQLException {
        storyDAO.delete(storyCreate);
    }
}
