package com.tkteam.reading;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.UUID;

/**
 * User: khiemvx
 * Date: 6/15/15
 * Time: 11:12 PM
 */
public class ApplicationStateHolder {
    // ------------------------------ FIELDS ------------------------------
    private static ApplicationStateHolder instance;
    MyActivity myActivity;
    private UUID userActiveId;
    private String userActiveName;

// -------------------------- STATIC METHODS --------------------------

    public ApplicationStateHolder(MyActivity myActivity) {
        this.myActivity = myActivity;
    }

    public static ApplicationStateHolder getInstance() {
        return instance;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public static ApplicationStateHolder getInstance(MyActivity myActivity) {
        if (instance == null) {
            instance = new ApplicationStateHolder(myActivity);
        } else {
            instance.myActivity = myActivity;
        }
        return instance;
    }

// --------------------- GETTER / SETTER METHODS ---------------------


    public MyActivity getMyActivity() {
        return myActivity;
    }

    public boolean isVoiceOfQuestion() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        return prefs.getBoolean("IS_VOICE_OF_QUESTION", true);
    }

    public void setVoiceOfQuestion(boolean isVoiceOfQuestion) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_VOICE_OF_QUESTION", isVoiceOfQuestion);
        editor.commit();
    }

    public boolean isReadStory() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        return prefs.getBoolean("IS_READ_STORY", true);
    }

    public void setReadStory(boolean isReadStory) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_READ_STORY", isReadStory);
        editor.commit();
    }

    public boolean isAward() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        return prefs.getBoolean("IS_AWARD", true);
    }

    public void setAward(boolean isAward) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_AWARD", isAward);
        editor.commit();
    }

    public boolean isVoiceOfWord() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        return prefs.getBoolean("IS_VOICE_OF_WORD", true);
    }

    public void setVoiceOfWord(boolean isVoiceOfWord) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_VOICE_OF_WORD", isVoiceOfWord);
        editor.commit();
    }

    public boolean isPracticeMode() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        return prefs.getBoolean("IS_PRACTICE_MODE", true);
    }

    public void setPracticeMode(boolean isPracticeMode) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("IS_PRACTICE_MODE", isPracticeMode);
        editor.commit();
    }

    public UUID getUserActiveId() {
        return userActiveId;
    }

    public void setUserActiveId(UUID userActiveId) {
        this.userActiveId = userActiveId;
    }

    public String getUserActiveName() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        return prefs.getString("USER_NAME", "User Default");
    }

    public void setUserActiveName(String userActiveName) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getMyActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USER_NAME", userActiveName);
        editor.commit();
    }
}
