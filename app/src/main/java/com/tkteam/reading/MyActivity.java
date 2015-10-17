package com.tkteam.reading;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.tkteam.reading.base.BaseActivity;
import com.tkteam.reading.dao.entites.User;
import com.tkteam.reading.service.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Trung on 6/2/2015.
 */
public class MyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ApplicationStateHolder.getInstance(this);
            List<User> userList = UserService.getInstance(this).findAll();
            if (userList == null || userList.size() == 0) {
                User user = new User();
                user.setCurrentUser(true);
                user.setName("User Default");
                ApplicationStateHolder.getInstance().setUserActiveName(user.getName());
                UserService.getInstance(this).createOrUpdate(user);
                ApplicationStateHolder.getInstance().setPracticeMode(true);
                ApplicationStateHolder.getInstance().setAward(true);
                ApplicationStateHolder.getInstance().setVoiceOfWord(true);
                ApplicationStateHolder.getInstance().setReadStory(true);
                ApplicationStateHolder.getInstance().setVoiceOfQuestion(true);
            }
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error when create database");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
