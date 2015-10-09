package com.tkteam.reading.dao.entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Khiemvx on 6/6/2015.
 */
@DatabaseTable(tableName = StoryCreate.TABLE_NAME)
public class StoryCreate extends Base {
    public static final String TABLE_NAME = "story_create";
    public static final String STORY_NAME = "title";
    public static final String STORY_CONTENT = "content";
    public static final String IMAGE_URL = "thumb_image";
    public static final String NUMBER_QUESTION_ANSWERED = "number_question_answered";
    public static final String NUMBER_ANSWER_CORRECT = "number_answered_correct";

    @DatabaseField
    private String title;
    @DatabaseField
    private String content;
    @DatabaseField
    private String thumb_image;
    @DatabaseField
    private String number_question_answered;
    @DatabaseField
    private String number_answered_correct;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getNumberQuestionAnswered() {
        return number_question_answered;
    }

    public void setNumberQuestionAnswered(String number_question_answered) {
        this.number_question_answered = number_question_answered;
    }

    public String getNumberAnsweredCorrect() {
        return number_answered_correct;
    }

    public void setNumberAnsweredCorrect(String number_answered_correct) {
        this.number_answered_correct = number_answered_correct;
    }
}
