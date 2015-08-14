package com.tkteam.reading.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.dao.DatabaseHelper;
import com.tkteam.reading.ui.event.DeleteStoryEvent;
import com.tkteam.reading.ui.group.Group;
import com.tkteam.reading.ui.group.ManageStoryGroup;
import com.tkteam.reading.ui.group.ViewHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by khiemvx on 5/27/15.
 */
public class CommonAdapter<T extends Group> extends BaseAdapter {
    private List<T> groupList = new ArrayList<>();
    private Context context;
    private boolean isShowDeleteButton = false;

    public CommonAdapter(Context context, List<T> groupList) {
        this.context = context;
        this.groupList = groupList;
        for (T t : groupList) {
            t.setContext(context);
        }
        EventBus.getDefault().register(this);
    }

    @Override
    public int getCount() {
        return groupList.size();
    }

    @Override
    public Object getItem(int position) {
        return groupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        T commonGroup = groupList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(commonGroup.getLayout(), parent, false);
            holder = new ViewHolder();
            commonGroup.findById(holder, convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        commonGroup.setDataToView(holder, convertView, isShowDeleteButton);
        return convertView;
    }

    public void onEventMainThread(DeleteStoryEvent event) {
        try {
            DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryCreateDao().deleteById(event.getStoryId());
        } catch (SQLException e) {
            Log.e("Common Adapter", "Can't delete this sotry");
        }
        ManageStoryGroup temp = null;
        for (T storyGroup : groupList)
            if (((ManageStoryGroup) storyGroup).getStoryId().equals(event.getStoryId())) {
                temp = (ManageStoryGroup) storyGroup;
                break;
            }
        if (temp != null)
            groupList.remove(temp);
        notifyDataSetChanged();
    }

    public List<T> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<T> groupList) {
        this.groupList = groupList;
    }

    public boolean isShowDeleteButton() {
        return isShowDeleteButton;
    }

    public void setShowDeleteButton(boolean isShowDeleteButton) {
        this.isShowDeleteButton = isShowDeleteButton;
    }
}
