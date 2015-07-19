package com.tkteam.reading.ui.fragment;

import android.database.Cursor;
import android.widget.GridView;

import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.dao.DatabaseHelper;
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.service.StoryService;
import com.tkteam.reading.ui.adapter.CommonAdapter;
import com.tkteam.reading.ui.group.LessonGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Trung on 5/26/2015.
 */
public class StartFragment extends BaseFragment {

    @InjectView(R.id.grid_view)
    GridView lvContent;

    @Override
    public int getLayout() {
        return R.layout.start_fragment;
    }

    @Override
    public void setupView() {
        List<LessonGroup> lessonGroupList = LessonGroup.convertFromStory(getDataFromDataBase());
        CommonAdapter commonAdapter = new CommonAdapter(getActivity(), lessonGroupList);
        lvContent.setAdapter(commonAdapter);
    }

    private List<Story> getDataFromDataBase() {
        List<Story> storyList = new ArrayList<>();
        Cursor c = DatabaseHelper.getInstance(getActivity()).getReadableDatabase().rawQuery("SELECT * FROM  story", new String[]{});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                storyList.add(StoryService.getInstance(getActivity()).convertDataToObject(c, new Story()));
            } while (c.moveToNext());
            c.close();
        }
        return storyList;
    }
}
