package com.tkteam.reading.ui.fragment;

import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.base.event.ChangedFragmentEvent;
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.service.StoryService;

import java.sql.SQLException;
import java.util.UUID;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Khiemvx on 6/6/2015.
 */
public class CreateStoryFragment extends BaseFragment {

    @InjectView(R.id.etTitle)
    EditText etTitle;
    @InjectView(R.id.etContent)
    EditText etContent;
    @InjectView(R.id.created_fragment_ivNext)
    ImageView ivNext;
    @InjectView(R.id.ivStory)
    ImageView ivStory;
    @InjectView(R.id.created_fragment_ivBack)
    ImageView ivBack;

    @Override
    public int getLayout() {
        return R.layout.created_story_fragment;
    }

    @Override
    public void setupView() {

    }

    @OnClick(R.id.ivStory)
    public void selectImageForStory() {

    }

    @OnClick(R.id.created_fragment_ivNext)
    public void clickStart() {
        Story story = new Story();
        UUID storyId = UUID.randomUUID();
        story.setStoryName(etTitle.getText().toString());
        story.setStoryContent(etContent.getText().toString());
        story.setId(storyId);
        Log.i("UUID", "UUID Story: " + storyId);
        try {
            StoryService.getInstance(getActivity()).createOrUpdate(story);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        EventBus.getDefault().post(new ChangedFragmentEvent(new CreateQuestionFragment(storyId)));
    }

    @OnClick(R.id.created_fragment_ivBack)
    public void onBack() {
        getActivity().onBackPressed();
    }

}
