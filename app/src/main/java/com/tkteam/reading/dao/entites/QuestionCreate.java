package com.tkteam.reading.dao.entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.UUID;

/**
 * Created by Khiemvx on 6/7/2015.
 */
@DatabaseTable(tableName = QuestionCreate.TABLE_NAME)
public class QuestionCreate extends Base {
    public static final String TABLE_NAME = "question_create";
    public static final String QUESTION = "question";
    public static final String ANSWER_CORRECT = "answerCorrect";
    public static final String ANSWER_ONE = "answerOne";
    public static final String ANSWER_TWO = "answerTwo";
    public static final String ANSWER_THREE = "answerThree";
    public static final String ANSWER_FOUR = "answerFour";
    public static final String ANSWER_HINT = "answerHint";
    public static final String STORY_ID = "storyId";

    @DatabaseField
    private String question;
    @DatabaseField
    private String answerCorrect;
    @DatabaseField
    private String answerOne;
    @DatabaseField
    private String answerTwo;
    @DatabaseField
    private String answerThree;
    @DatabaseField
    private String answerFour;
    @DatabaseField
    private String answerHint;
    @DatabaseField
    private UUID storyId;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerCorrect() {
        return answerCorrect;
    }

    public void setAnswerCorrect(String answerCorrect) {
        this.answerCorrect = answerCorrect;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public UUID getStoryId() {
        return storyId;
    }

    public void setStoryId(UUID storyId) {
        this.storyId = storyId;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }

    public String getAnswerHint() {
        return answerHint;
    }

    public void setAnswerHint(String answerHint) {
        this.answerHint = answerHint;
    }
}
