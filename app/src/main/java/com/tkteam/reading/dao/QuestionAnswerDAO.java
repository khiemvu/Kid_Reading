package com.tkteam.reading.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tkteam.reading.dao.entites.QuestionAnswers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Khiemvx on 6/7/2015.
 */
public class QuestionAnswerDAO {
    private static QuestionAnswerDAO instance;
    Dao<QuestionAnswers, UUID> questionAnswerDAO;

    public QuestionAnswerDAO(Context context) throws SQLException {
        this.questionAnswerDAO = DatabaseHelper.getInstance(context).getQuestionAnswerDao();
    }

    public static QuestionAnswerDAO getInstance(Context context) throws SQLException {
        if (instance == null) {
            instance = new QuestionAnswerDAO(context);
        }
        return instance;
    }

    public QuestionAnswers create(QuestionAnswers story) throws SQLException {
        return questionAnswerDAO.createIfNotExists(story);
    }

    public void update(QuestionAnswers story) throws SQLException {
        questionAnswerDAO.update(story);
    }

    public QuestionAnswers findById(UUID id) throws SQLException, IOException {
        return questionAnswerDAO.queryForId(id);
    }

    public List<QuestionAnswers> findAll() throws SQLException {
        return questionAnswerDAO.queryForAll();
    }

    public void createOrUpdate(QuestionAnswers story) throws SQLException {
        questionAnswerDAO.createOrUpdate(story);
    }

    public void delete(QuestionAnswers story) throws SQLException {
        questionAnswerDAO.delete(story);
    }
}
