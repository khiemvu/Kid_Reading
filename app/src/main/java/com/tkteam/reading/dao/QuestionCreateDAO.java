package com.tkteam.reading.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tkteam.reading.dao.entites.QuestionCreate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Khiemvx on 6/7/2015.
 */
public class QuestionCreateDAO {
    private static QuestionCreateDAO instance;
    Dao<QuestionCreate, UUID> questionAnswerDAO;

    public QuestionCreateDAO(Context context) throws SQLException {
        this.questionAnswerDAO = DatabaseHelper.getInstance(context).getQuestionAnswerDao();
    }

    public static QuestionCreateDAO getInstance(Context context) throws SQLException {
        if (instance == null) {
            instance = new QuestionCreateDAO(context);
        }
        return instance;
    }

    public QuestionCreate create(QuestionCreate story) throws SQLException {
        return questionAnswerDAO.createIfNotExists(story);
    }

    public void update(QuestionCreate story) throws SQLException {
        questionAnswerDAO.update(story);
    }

    public QuestionCreate findById(UUID id) throws SQLException, IOException {
        return questionAnswerDAO.queryForId(id);
    }

    public List<QuestionCreate> findAll() throws SQLException {
        return questionAnswerDAO.queryForAll();
    }

    public void createOrUpdate(QuestionCreate story) throws SQLException {
        questionAnswerDAO.createOrUpdate(story);
    }

    public void delete(QuestionCreate story) throws SQLException {
        questionAnswerDAO.delete(story);
    }
}
