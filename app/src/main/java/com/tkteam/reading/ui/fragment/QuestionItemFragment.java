package com.tkteam.reading.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.R;
import com.tkteam.reading.dao.entites.Question;
import com.tkteam.reading.utils.StringUtils;

/**
 * Created by Khiemvx on 7/19/2015.
 */
public class QuestionItemFragment extends Fragment {
    Question question;
    int currentPosition;

    public static QuestionItemFragment newInstance(Question question, int currentPosition) {
        QuestionItemFragment f = new QuestionItemFragment();
        f.question = question;
        f.currentPosition = currentPosition;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.question_answer_fragment, container, false);
        final TextView tvQuestion = (TextView) v.findViewById(R.id.tvQuestion);
        RadioButton answer1 = (RadioButton) v.findViewById(R.id.tvAnswer1);
        RadioButton answer2 = (RadioButton) v.findViewById(R.id.tvAnswer2);
        RadioButton answer3 = (RadioButton) v.findViewById(R.id.tvAnswer3);
        RadioButton answer4 = (RadioButton) v.findViewById(R.id.tvAnswer4);
        final ImageView ivCorrect = (ImageView) v.findViewById(R.id.ivCorrect);
        answer1.setVisibility(StringUtils.isEmpty(question.getAnswer_1()) ? View.GONE : View.VISIBLE);
        answer2.setVisibility(StringUtils.isEmpty(question.getAnswer_2()) ? View.GONE : View.VISIBLE);
        answer3.setVisibility(StringUtils.isEmpty(question.getAnswer_3()) ? View.GONE : View.VISIBLE);
        answer4.setVisibility(StringUtils.isEmpty(question.getAnswer_4()) ? View.GONE : View.VISIBLE);
        tvQuestion.setText(question.getQuestion());
        answer1.setText(question.getAnswer_1());
        answer2.setText(question.getAnswer_2());
        answer3.setText(question.getAnswer_3());
        answer4.setText(question.getAnswer_4());
        answer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(question.getAnswer_1().equals(question.getCorrect_answer())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                }
            }
        });
        answer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(question.getAnswer_2().equals(question.getCorrect_answer())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                }
            }
        });
        answer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(question.getAnswer_3().equals(question.getCorrect_answer())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                }
            }
        });
        answer4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(question.getAnswer_4().equals(question.getCorrect_answer())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                }
            }
        });
        RadioGroup radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ReadStoryFragment.numberAnswered = currentPosition;
            }
        });
        return v;
    }

}


