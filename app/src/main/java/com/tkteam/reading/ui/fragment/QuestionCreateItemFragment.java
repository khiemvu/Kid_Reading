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
import com.tkteam.reading.dao.entites.QuestionCreate;
import com.tkteam.reading.utils.StringUtils;

import java.util.Locale;

/**
 * Created by Khiemvx on 8/22/2015.
 */
public class QuestionCreateItemFragment extends Fragment {
    static TextToSpeech textToSpeech;
    QuestionCreate questionCreate;
    int currentPosition;
    MediaPlayer mPlayer;

    public static QuestionCreateItemFragment newInstance(QuestionCreate questionCreate, int currentPosition) {
        QuestionCreateItemFragment f = new QuestionCreateItemFragment();
        f.questionCreate = questionCreate;
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
        answer1.setVisibility(StringUtils.isEmpty(questionCreate.getAnswerOne()) ? View.GONE : View.VISIBLE);
        answer2.setVisibility(StringUtils.isEmpty(questionCreate.getAnswerTwo()) ? View.GONE : View.VISIBLE);
        answer3.setVisibility(StringUtils.isEmpty(questionCreate.getAnswerThree()) ? View.GONE : View.VISIBLE);
        answer4.setVisibility(StringUtils.isEmpty(questionCreate.getAnswerFour()) ? View.GONE : View.VISIBLE);
        tvQuestion.setText(questionCreate.getQuestion());
        answer1.setText(questionCreate.getAnswerOne());
        answer2.setText(questionCreate.getAnswerTwo());
        answer3.setText(questionCreate.getAnswerThree());
        answer4.setText(questionCreate.getAnswerFour());
        if (ApplicationStateHolder.getInstance().isPracticeMode() && ApplicationStateHolder.getInstance().isAward())
            ivCorrect.setVisibility(View.VISIBLE);
        else
            ivCorrect.setVisibility(View.GONE);
        answer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(questionCreate.getAnswerOne().equals(questionCreate.getAnswerCorrect())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                    if (questionCreate.getAnswerOne().equals(questionCreate.getAnswerCorrect()))
                        ReadStoryFragment.numberAnsweredRight++;
                    soundTrueOrFalseAlert(questionCreate.getAnswerOne().equals(questionCreate.getAnswerCorrect()));
                }
                if (ApplicationStateHolder.getInstance().isVoiceOfQuestion()) {
                    textToSpeech.speak(questionCreate.getAnswerOne(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        answer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(questionCreate.getAnswerTwo().equals(questionCreate.getAnswerCorrect())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                    if (questionCreate.getAnswerTwo().equals(questionCreate.getAnswerCorrect()))
                        ReadStoryFragment.numberAnsweredRight++;
                    soundTrueOrFalseAlert(questionCreate.getAnswerTwo().equals(questionCreate.getAnswerCorrect()));
                }
                if (ApplicationStateHolder.getInstance().isVoiceOfQuestion()) {
                    textToSpeech.speak(questionCreate.getAnswerTwo(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        answer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(questionCreate.getAnswerThree().equals(questionCreate.getAnswerCorrect())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                    if (questionCreate.getAnswerThree().equals(questionCreate.getAnswerCorrect()))
                        ReadStoryFragment.numberAnsweredRight++;
                    soundTrueOrFalseAlert(questionCreate.getAnswerThree().equals(questionCreate.getAnswerCorrect()));
                }
                if (ApplicationStateHolder.getInstance().isVoiceOfQuestion()) {
                    textToSpeech.speak(questionCreate.getAnswerThree(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        answer4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ivCorrect.setBackground(questionCreate.getAnswerFour().equals(questionCreate.getAnswerCorrect())
                            ? getResources().getDrawable(R.drawable.right_answer) : getResources().getDrawable(R.drawable.wrong_answer));
                    if (questionCreate.getAnswerFour().equals(questionCreate.getAnswerCorrect()))
                        ReadStoryFragment.numberAnsweredRight++;
                    soundTrueOrFalseAlert(questionCreate.getAnswerFour().equals(questionCreate.getAnswerCorrect()));
                }
                if (ApplicationStateHolder.getInstance().isVoiceOfQuestion()) {
                    textToSpeech.speak(questionCreate.getAnswerFour(), TextToSpeech.QUEUE_FLUSH, null);
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


