package com.tkteam.reading.service;

import android.content.Context;
import android.database.Cursor;

import com.tkteam.reading.dao.QuestionDAO;
import com.tkteam.reading.dao.entites.Question;
import com.tkteam.reading.dao.entites.Story;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Khiemvx on 6/20/2015.
 */
public class QuestionSerVice {
    public static QuestionSerVice instance;
    private Context context;

    public QuestionSerVice(Context context) {
        this.context = context;

    }

    public static QuestionSerVice getInstance(Context context) {
        if (instance == null) {
            instance = new QuestionSerVice(context);
        }
        return instance;
    }

    public Question create(Question question) throws SQLException {
        return QuestionDAO.getInstance(context).create(question);
    }

    public Question findById(UUID id) throws SQLException, IOException {
        return QuestionDAO.getInstance(context).findById(id);
    }

    public List<Question> findAll() throws SQLException {
        return QuestionDAO.getInstance(context).findAll();
    }

    public void update(Question question) throws SQLException {
        QuestionDAO.getInstance(context).update(question);
    }

    public void delete(Question question) throws SQLException {
        QuestionDAO.getInstance(context).delete(question);
    }

    public void createOrUpdate(Question question) throws SQLException {
        QuestionDAO.getInstance(context).createOrUpdate(question);
    }

    public Question convertDataToObject(Cursor c, Question question) {
        question.setStory_id(c.getString(c.getColumnIndex(Question.STORY_ID)));
        question.setQuestion(c.getString(c.getColumnIndex(Question.QUESTION)));
        question.setAnswer_1(c.getString(c.getColumnIndex(Question.ANSWER_ONE)));
        question.setAnswer_2(c.getString(c.getColumnIndex(Question.ANSWER_TWO)));
        question.setAnswer_3(c.getString(c.getColumnIndex(Question.ANSWER_THREE)));
        question.setAnswer_4(c.getString(c.getColumnIndex(Question.ANSWER_FOUR)));
        question.setCorrect_answer(c.getString(c.getColumnIndex(Question.ANSWER_CORRECT)));
        question.setHints(c.getString(c.getColumnIndex(Question.ANSWER_HINT)));
        return question;
    }
}
