package com.tkteam.reading.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tkteam.reading.dao.entites.QuestionCreate;
import com.tkteam.reading.ui.fragment.QuestionCreateItemFragment;

import java.util.List;

/**
 * Created by Khiemvx on 8/22/2015.
 */
public class ReadStoryCreateAdapter extends FragmentPagerAdapter {
    private List<QuestionCreate> questions;

    public ReadStoryCreateAdapter(FragmentManager fm, List<QuestionCreate> questions) {
        super(fm);
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        if (questions != null && questions.size() > 0) {
            return QuestionCreateItemFragment.newInstance(questions.get(position), position);
        } else {
            return QuestionCreateItemFragment.newInstance(null, 0);
        }
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    public List<QuestionCreate> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionCreate> questions) {
        this.questions = questions;
    }
}