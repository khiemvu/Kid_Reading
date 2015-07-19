package com.tkteam.reading.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.tkteam.reading.dao.entites.Question;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Khiemvx on 6/20/2015.
 */
public class QuestionDAO {
    private static QuestionDAO instance;
    Dao<Question, UUID> questionDAO;

    public QuestionDAO(Context context) throws SQLException {
        this.questionDAO = DatabaseHelper.getInstance(context).getQuestionDao();
    }

    public static QuestionDAO getInstance(Context context) throws SQLException {
        if (instance == null) {
            instance = new QuestionDAO(context);
        }
        return instance;
    }

    public Question create(Question question) throws SQLException {
        return questionDAO.createIfNotExists(question);
    }

    public void update(Question story) throws SQLException {
        questionDAO.update(story);
    }

    public Question findById(UUID id) throws SQLException, IOException {
        return questionDAO.queryForId(id);
    }

    public List<Question> findAll() throws SQLException {
        return questionDAO.queryForAll();
    }

    public void createOrUpdate(Question story) throws SQLException {
        questionDAO.createOrUpdate(story);
    }

    public void delete(Question story) throws SQLException {
        questionDAO.delete(story);
    }
}
