package com.tkteam.reading.service;

import android.content.Context;

import com.tkteam.reading.dao.QuestionAnswerDAO;
import com.tkteam.reading.dao.entites.QuestionAnswers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Khiemvx on 6/7/2015.
 */
public class QuestionAnswerService {

    public static QuestionAnswerService instance;
    private Context context;

    public QuestionAnswerService(Context context) {
        this.context = context;

    }

    public static QuestionAnswerService getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionAnswerService(context);
        }
        return instance;
    }

    public QuestionAnswers create(QuestionAnswers questionAnswers) throws SQLException {
        return QuestionAnswerDAO.getInstance(context).create(questionAnswers);
    }

    public QuestionAnswers findById(UUID id) throws SQLException, IOException {
        return QuestionAnswerDAO.getInstance(context).findById(id);
    }

    public List<QuestionAnswers> findAll() throws SQLException {
        return QuestionAnswerDAO.getInstance(context).findAll();
    }

    public void update(QuestionAnswers questionAnswers) throws SQLException {
        QuestionAnswerDAO.getInstance(context).update(questionAnswers);
    }

    public void delete(QuestionAnswers questionAnswers) throws SQLException {
        QuestionAnswerDAO.getInstance(context).delete(questionAnswers);
    }

    public void createOrUpdate(QuestionAnswers questionAnswers) throws SQLException {
        QuestionAnswerDAO.getInstance(context).createOrUpdate(questionAnswers);
    }
}
