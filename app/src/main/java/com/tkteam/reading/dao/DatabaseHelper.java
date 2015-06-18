package com.tkteam.reading.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.tkteam.reading.R;
import com.tkteam.reading.dao.entites.QuestionAnswers;
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.dao.entites.User;
import com.tkteam.reading.utils.FileUtils;

import java.sql.SQLException;
import java.util.UUID;

/**
 * Created by trungpt on 3/31/15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    public static String DATABASE_NAME = "ATA.db";
    private static DatabaseHelper instance;
    private Context context;
    private Dao<User, UUID> userDao;
    private Dao<Story, UUID> storyDao;
    private Dao<QuestionAnswers, UUID> questionAnswerDao;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static DatabaseHelper getInstance(Context ctx) {
        if (instance == null) {
            instance = new DatabaseHelper(ctx.getApplicationContext());
        }
        return instance;
    }

    public Dao<User, UUID> getUserDao() throws SQLException {
        if (userDao == null) {
            userDao = getDao(User.class);
            ((BaseDaoImpl) userDao).initialize();
        }
        return userDao;
    }

    public Dao<Story, UUID> getStoryDao() throws SQLException {
        if (storyDao == null) {
            storyDao = getDao(Story.class);
            ((BaseDaoImpl) storyDao).initialize();
        }
        return storyDao;
    }

    public Dao<QuestionAnswers, UUID> getQuestionAnswerDao() throws SQLException {
        if (questionAnswerDao == null) {
            questionAnswerDao = getDao(QuestionAnswers.class);
            ((BaseDaoImpl) questionAnswerDao).initialize();
        }
        return questionAnswerDao;
    }

// -------------------------- OTHER METHODS --------------------------

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, User.class);
//            TableUtils.createTable(connectionSource, Story.class);
//            TableUtils.createTable(connectionSource, QuestionAnswers.class);
            String story = FileUtils.readRawFileAsString(context, R.raw.story);
//            String question = FileUtils.readRawFileAsString(context, R.raw.questions);
            String[] stories = story.split(";");
//            String[] questions = question.split(";");
            for (String query : stories)
            {
                sqLiteDatabase.execSQL(query);
            }

        }
        catch (Exception e)
        {
            Log.e(this.getClass().getName(), e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int oldVersion,
                          int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, true);
            TableUtils.dropTable(connectionSource, Story.class, true);
            TableUtils.dropTable(connectionSource, QuestionAnswers.class, true);
            TableUtils.createTable(connectionSource, User.class);
            TableUtils.createTable(connectionSource, QuestionAnswers.class);
            TableUtils.createTable(connectionSource, Story.class);

        } catch (SQLException e) {
        }
    }

    public void dropAllDatabase() throws SQLException {
        TableUtils.dropTable(connectionSource, User.class, true);
        TableUtils.dropTable(connectionSource, Story.class, true);
        TableUtils.dropTable(connectionSource, QuestionAnswers.class, true);

    }

    public void createTables() throws SQLException {
        TableUtils.createTable(connectionSource, User.class);
        TableUtils.createTable(connectionSource, Story.class);
        TableUtils.createTable(connectionSource, QuestionAnswers.class);
    }
}
