package com.tkteam.reading.dao.entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Khiemvx on 6/20/2015.
 */
@DatabaseTable(tableName = QuestionCreate.TABLE_NAME)
public class Question extends Base {
    public static final String TABLE_NAME = "questions";
    public static final String QUESTION = "question";
    public static final String ANSWER_CORRECT = "correct_answer";
    public static final String ANSWER_ONE = "answer_1";
    public static final String ANSWER_TWO = "answer_2";
    public static final String ANSWER_THREE = "answer_3";
    public static final String ANSWER_FOUR = "answer_4";
    public static final String ANSWER_HINT = "hints";
    public static final String STORY_ID = "story_id";

    @DatabaseField
    private String question;
    @DatabaseField
    private String correct_answer;
    @DatabaseField
    private String answer_1;
    @DatabaseField
    private String answer_2;
    @DatabaseField
    private String answer_3;
    @DatabaseField
    private String answer_4;
    @DatabaseField
    private String hints;
    @DatabaseField
    private String story_id;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getStory_id() {
        return story_id;
    }

    public void setStory_id(String story_id) {
        this.story_id = story_id;
    }

    public String getAnswer_4() {
        return answer_4;
    }

    public void setAnswer_4(String answer_4) {
        this.answer_4 = answer_4;
    }

    public String getHints() {
        return hints;
    }

    public void setHints(String hints) {
        this.hints = hints;
    }
}

