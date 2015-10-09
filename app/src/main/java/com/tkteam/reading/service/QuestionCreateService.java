package com.tkteam.reading.service;

import android.content.Context;
import android.database.Cursor;

import com.tkteam.reading.dao.QuestionCreateDAO;
import com.tkteam.reading.dao.entites.QuestionCreate;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Khiemvx on 6/7/2015.
 */
public class QuestionCreateService {

    public static QuestionCreateService instance;
    private Context context;

    public QuestionCreateService(Context context) {
        this.context = context;

    }

    public static QuestionCreateService getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionCreateService(context);
        }
        return instance;
    }

    public QuestionCreate create(QuestionCreate questionCreate) throws SQLException {
        return QuestionCreateDAO.getInstance(context).create(questionCreate);
    }

    public QuestionCreate findById(UUID id) throws SQLException, IOException {
        return QuestionCreateDAO.getInstance(context).findById(id);
    }

    public List<QuestionCreate> findAll() throws SQLException {
        return QuestionCreateDAO.getInstance(context).findAll();
    }

    public void update(QuestionCreate questionCreate) throws SQLException {
        QuestionCreateDAO.getInstance(context).update(questionCreate);
    }

    public void delete(QuestionCreate questionCreate) throws SQLException {
        QuestionCreateDAO.getInstance(context).delete(questionCreate);
    }

    public void createOrUpdate(QuestionCreate questionCreate) throws SQLException {
        QuestionCreateDAO.getInstance(context).createOrUpdate(questionCreate);
    }

    public QuestionCreate convertDataToObject(Cursor c, QuestionCreate questionCreate) {
        questionCreate.setStoryId(UUID.fromString(c.getString(c.getColumnIndex(QuestionCreate.STORY_ID))));
        questionCreate.setQuestion(c.getString(c.getColumnIndex(QuestionCreate.QUESTION)));
        questionCreate.setAnswerOne(c.getString(c.getColumnIndex(QuestionCreate.ANSWER_ONE)));
        questionCreate.setAnswerTwo(c.getString(c.getColumnIndex(QuestionCreate.ANSWER_TWO)));
        questionCreate.setAnswerThree(c.getString(c.getColumnIndex(QuestionCreate.ANSWER_THREE)));
        questionCreate.setAnswerFour(c.getString(c.getColumnIndex(QuestionCreate.ANSWER_FOUR)));
        questionCreate.setAnswerCorrect(c.getString(c.getColumnIndex(QuestionCreate.ANSWER_CORRECT)));
        questionCreate.setAnswerHint(c.getString(c.getColumnIndex(QuestionCreate.ANSWER_HINT)));
        return questionCreate;
    }
}
