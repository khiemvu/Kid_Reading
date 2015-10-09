package com.tkteam.reading.ui.fragment;

import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.service.StoryCreateService;
import com.tkteam.reading.service.StoryService;
import com.tkteam.reading.ui.adapter.CommonAdapter;
import com.tkteam.reading.ui.event.ResetAllStoryEvent;
import com.tkteam.reading.ui.group.ProgressGroup;
import com.tkteam.reading.utils.ProgressDialogHolder;

import java.sql.SQLException;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by khiemvx on 5/26/2015.
 */
public class ProgressFragment extends BaseFragment {
    @InjectView(R.id.lvContent)
    ListView lvContent;
    @InjectView(R.id.ivBack)
    ImageView ivBack;
    @InjectView(R.id.ivReset)
    ImageView ivReset;
    @InjectView(R.id.rbSystemLesson)
    RadioButton rbSystemLesson;
    @InjectView(R.id.rbCreateLesson)
    RadioButton rbCreateLesson;

    CommonAdapter commonAdapter;
    boolean showDeleteButton = false, isCreateStory = false;
    List<ProgressGroup> storyListSystem = null;
    List<ProgressGroup> storyListCreated = null;

    @Override
    public int getLayout() {
        return R.layout.progress_fragment;
    }

    @Override
    public void setupView() {
        showListSystemLesson();
    }

    @OnClick(R.id.ivReset)
    public void onReset() {
        ProgressDialogHolder.getInstance().showDialogWithoutMessage();
        EventBus.getDefault().post(new ResetAllStoryEvent(isCreateStory));
    }

    @OnClick(R.id.rbCreateLesson)
    public void showListCreateLesson() {
        isCreateStory = true;
        try {
            storyListCreated = ProgressGroup.convertFromStoryCreate(StoryCreateService.getInstance(getActivity()).findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (storyListCreated != null) {
            commonAdapter = new CommonAdapter(getActivity(), storyListCreated);
            lvContent.setAdapter(commonAdapter);
        }
    }

    @OnClick(R.id.rbSystemLesson)
    public void showListSystemLesson() {
        isCreateStory = false;
        try {
            storyListSystem = ProgressGroup.convertFromStorySystem(StoryService.getInstance(getActivity()).findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (storyListSystem != null) {
            commonAdapter = new CommonAdapter(getActivity(), storyListSystem);
            lvContent.setAdapter(commonAdapter);
        }
    }

    @OnClick(R.id.ivBack)
    public void onBack() {
        getActivity().onBackPressed();
    }
}
