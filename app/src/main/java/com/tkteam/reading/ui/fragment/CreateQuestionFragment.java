package com.tkteam.reading.ui.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.ImageView;

import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.dao.entites.QuestionCreate;
import com.tkteam.reading.service.QuestionCreateService;
import com.tkteam.reading.utils.StringUtils;

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
    @InjectView(R.id.ivCancelCreateQuestion)
    ImageView ivCancelCreateQuestion;
    @InjectView(R.id.ivCancel)
    ImageView ivCancel;
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
    }

    @OnClick(R.id.ivAdd)
    void onCreateQuestionAndAnswer() {
        if (StringUtils.isEmpty(etQuestion.getText().toString()) || StringUtils.isEmpty(etAnswer1.getText().toString())
                || StringUtils.isEmpty(etAnswer2.getText().toString()) || StringUtils.isEmpty(etAnswer3.getText().toString())
                || StringUtils.isEmpty(etAnswer4.getText().toString())) {
            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(ApplicationStateHolder.getInstance().getMyActivity());
            alertDialog.setTitle("Error");
            alertDialog.setMessage("All fields is required");
            alertDialog.setCancelable(false);
            alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        } else {
            QuestionCreate questionCreate = new QuestionCreate();
            questionCreate.setStoryId(storyId);
            questionCreate.setQuestion(etQuestion.getText().toString());
            questionCreate.setAnswerCorrect(etAnswer1.getText().toString());
            questionCreate.setAnswerOne(etAnswer1.getText().toString());
            questionCreate.setAnswerTwo(etAnswer2.getText().toString());
            questionCreate.setAnswerThree(etAnswer3.getText().toString());
            questionCreate.setAnswerFour(etAnswer4.getText().toString());
            try {
                QuestionCreateService.getInstance(getActivity()).createOrUpdate(questionCreate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            resetAllView();
        }
    }

    private void resetAllView() {
        etQuestion.setText("");
        etAnswer1.setText("");
        etAnswer2.setText("");
        etAnswer3.setText("");
        etAnswer4.setText("");
    }

    @OnClick({R.id.ivBack, R.id.ivCancelCreateQuestion, R.id.ivCancel})
    public void onBack() {
        getActivity().onBackPressed();
    }
}
