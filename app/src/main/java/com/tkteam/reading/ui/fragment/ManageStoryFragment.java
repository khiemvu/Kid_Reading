package com.tkteam.reading.ui.fragment;

import android.widget.ImageView;
import android.widget.ListView;

import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.base.event.ChangedFragmentEvent;
import com.tkteam.reading.service.StoryCreateService;
import com.tkteam.reading.ui.adapter.CommonAdapter;
import com.tkteam.reading.ui.group.StoryGroup;

import java.sql.SQLException;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * Created by Trung on 5/26/2015.
 */
public class ManageStoryFragment extends BaseFragment {
    @InjectView(R.id.lvContent)
    ListView lvContent;
    @InjectView(R.id.ivBack)
    ImageView ivBack;

    @Override
    public int getLayout() {
        return R.layout.manage_story_fragment;
    }

    @Override
    public void setupView() {

        List<StoryGroup> storyList = null;
        try {
            storyList = StoryGroup.convertFromStory(StoryCreateService.getInstance(getActivity()).findAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (storyList != null) {
            CommonAdapter commonAdapter = new CommonAdapter(getActivity(), storyList);
            lvContent.setAdapter(commonAdapter);
        }
    }

    @OnClick(R.id.manage_story_ivAdd)
    public void clickStart() {
        EventBus.getDefault().post(new ChangedFragmentEvent(new CreateStoryFragment()));
    }

    @OnClick(R.id.ivBack)
    public void onBack() {
        getActivity().onBackPressed();
    }
}
