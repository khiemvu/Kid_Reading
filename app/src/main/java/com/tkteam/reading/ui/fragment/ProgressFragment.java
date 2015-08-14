package com.tkteam.reading.ui.fragment;

import android.widget.ImageView;
import android.widget.ListView;

import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.service.StoryService;
import com.tkteam.reading.ui.adapter.CommonAdapter;
import com.tkteam.reading.ui.group.ProgressGroup;

import java.sql.SQLException;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by khiemvx on 5/26/2015.
 */
public class ProgressFragment extends BaseFragment {
    @InjectView(R.id.lvContent)
    ListView lvContent;
    @InjectView(R.id.ivBack)
    ImageView ivBack;

    CommonAdapter commonAdapter;
    boolean showDeleteButton = false;
    List<ProgressGroup> storyListSystem = null;
    List<ProgressGroup> storyListCreated = null;


    @Override
    public int getLayout() {
        return R.layout.progress_fragment;
    }

    @Override
    public void setupView() {


        try {
            storyListSystem = ProgressGroup.convertFromStory(StoryService.getInstance(getActivity()).findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (storyListSystem != null) {
            commonAdapter = new CommonAdapter(getActivity(), storyListSystem);
            lvContent.setAdapter(commonAdapter);
        }
    }

//    @OnClick(R.id.manage_story_ivAdd)
//    public void clickStart() {
//        EventBus.getDefault().post(new ChangedFragmentEvent(new CreateStoryFragment()));
//    }


    @OnClick(R.id.ivBack)
    public void onBack() {
        getActivity().onBackPressed();
    }
}
