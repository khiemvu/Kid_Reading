package com.tkteam.reading.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tkteam.reading.dao.entites.Question;
import com.tkteam.reading.ui.fragment.QuestionItemFragment;

import java.util.List;

/**
 * Created by Khiemvx on 7/19/2015.
 */
public class ReadStoryAdapter extends FragmentPagerAdapter {
    private List<Question> questions;

    public ReadStoryAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        this.questions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        if (questions != null && questions.size() > 0) {
            return QuestionItemFragment.newInstance(questions.get(position), position);
        } else {
            return QuestionItemFragment.newInstance(null, 0);
        }
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}