package com.tkteam.reading.ui.fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.R;
import com.tkteam.reading.dao.entites.Question;
import com.tkteam.reading.utils.StringUtils;

import java.util.Locale;

/**
 * Created by Khiemvx on 7/19/2015.
 */
public class QuestionSystemItemFragment extends Fragment {
    static TextToSpeech textToSpeech;
    Question question;
    int currentPosition;
    MediaPlayer mPlayer;

    public static QuestionSystemItemFragment newInstance(Question question, int currentPosition) {
        QuestionSystemItemFragment f = new QuestionSystemItemFragment();
        f.question = question;
        f.currentPosition = currentPosition;
        textToSpeech = new TextToSpeech(ApplicationStateHolder.getInstance().getMyActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

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
        if (ApplicationStateHolder.getInstance().isPracticeMode() && ApplicationStateHolder.getInstance().isAward())
            ivCorrect.setVisibility(View.VISIBLE);
        else
            ivCorrect.setVisibility(View.GONE);
        answer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(question.getAnswer_1().equals(question.getCorrect_answer())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                    if (question.getAnswer_1().equals(question.getCorrect_answer()))
                        ReadStoryFragment.numberAnsweredRight++;
                    soundTrueOrFalseAlert(question.getAnswer_1().equals(question.getCorrect_answer()));
                    if (ApplicationStateHolder.getInstance().isVoiceOfQuestion()) {
                        textToSpeech.speak(question.getAnswer_1(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        });
        answer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(question.getAnswer_2().equals(question.getCorrect_answer())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                    if (question.getAnswer_2().equals(question.getCorrect_answer()))
                        ReadStoryFragment.numberAnsweredRight++;
                    soundTrueOrFalseAlert(question.getAnswer_2().equals(question.getCorrect_answer()));
                    if (ApplicationStateHolder.getInstance().isVoiceOfQuestion()) {
                        textToSpeech.speak(question.getAnswer_2(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });
        answer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(question.getAnswer_3().equals(question.getCorrect_answer())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                    if (question.getAnswer_3().equals(question.getCorrect_answer()))
                        ReadStoryFragment.numberAnsweredRight++;
                    soundTrueOrFalseAlert(question.getAnswer_3().equals(question.getCorrect_answer()));
                    if (ApplicationStateHolder.getInstance().isVoiceOfQuestion()) {
                        textToSpeech.speak(question.getAnswer_3(), TextToSpeech.QUEUE_FLUSH, null);
                    }
                }

            }
        });
        answer4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(question.getAnswer_4().equals(question.getCorrect_answer())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                    if (question.getAnswer_4().equals(question.getCorrect_answer()))
                        ReadStoryFragment.numberAnsweredRight++;
                    soundTrueOrFalseAlert(question.getAnswer_4().equals(question.getCorrect_answer()));
                    if (ApplicationStateHolder.getInstance().isVoiceOfQuestion()) {
                        textToSpeech.speak(question.getAnswer_4(), TextToSpeech.QUEUE_FLUSH, null);
                    }
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

    private void soundTrueOrFalseAlert(boolean isEqual) {
        int fileSound = isEqual ? R.raw.yes : R.raw.no;
        mPlayer = MediaPlayer.create(ApplicationStateHolder.getInstance().getMyActivity(), fileSound);
        if (ApplicationStateHolder.getInstance().isPracticeMode() && ApplicationStateHolder.getInstance().isAward())
            mPlayer.start();
    }
}


