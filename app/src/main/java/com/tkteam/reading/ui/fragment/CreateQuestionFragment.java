package com.tkteam.reading.ui.fragment;

import android.annotation.SuppressLint;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.dao.entites.QuestionAnswers;
import com.tkteam.reading.service.QuestionAnswerService;

import java.sql.SQLException;
import java.util.UUID;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Khiemvx on 6/6/2015.
 */
@SuppressLint("ValidFragment")
public class CreateQuestionFragment extends BaseFragment {

    @InjectView(R.id.etQuestion)
    EditText etQuestion;
    @InjectView(R.id.etAnswer1)
    EditText etAnswer1;
    @InjectView(R.id.etAnswer2)
    EditText etAnswer2;
    @InjectView(R.id.etAnswer3)
    EditText etAnswer3;
    @InjectView(R.id.etAnswer4)
    EditText etAnswer4;
    @InjectView(R.id.ivAdd)
    ImageView ivAdd;
    @InjectView(R.id.ivBack)
    ImageView ivBack;
    private UUID storyId;

    public CreateQuestionFragment(UUID storyId) {
        this.storyId = storyId;
    }

    @Override
    public int getLayout() {
        return R.layout.created_question_fragment;
    }

    @Override
    public void setupView() {
        Toast.makeText(getActivity(), storyId.toString(), Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.ivAdd)
    void onCreateQuestionAndAnswer() {
        QuestionAnswers questionAnswers = new QuestionAnswers();
        questionAnswers.setStoryId(storyId);
        questionAnswers.setQuestion(etQuestion.getText().toString());
        questionAnswers.setAnswerCorrect(etAnswer1.getText().toString());
        questionAnswers.setAnswerOne(etAnswer2.getText().toString());
        questionAnswers.setAnswerTwo(etAnswer3.getText().toString());
        questionAnswers.setAnswerThree(etAnswer4.getText().toString());
        try {
            QuestionAnswerService.getInstance(getActivity()).createOrUpdate(questionAnswers);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.ivBack)
    public void onBack() {
        getActivity().onBackPressed();
    }
}
