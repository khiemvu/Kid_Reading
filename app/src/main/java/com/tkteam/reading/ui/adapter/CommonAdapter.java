package com.tkteam.reading.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.dao.DatabaseHelper;
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.dao.entites.StoryCreate;
import com.tkteam.reading.dao.entites.User;
import com.tkteam.reading.service.StoryCreateService;
import com.tkteam.reading.service.StoryService;
import com.tkteam.reading.service.UserService;
import com.tkteam.reading.ui.event.AddUserEvent;
import com.tkteam.reading.ui.event.DeleteStoryEvent;
import com.tkteam.reading.ui.event.DeleteUserEvent;
import com.tkteam.reading.ui.event.EditUserRespontEvent;
import com.tkteam.reading.ui.event.ResetAllStoryEvent;
import com.tkteam.reading.ui.event.ResetOneStoryEvent;
import com.tkteam.reading.ui.event.SetActiveUserEvent;
import com.tkteam.reading.ui.group.Group;
import com.tkteam.reading.ui.group.ManageStoryGroup;
import com.tkteam.reading.ui.group.ProgressGroup;
import com.tkteam.reading.ui.group.UserGroup;
import com.tkteam.reading.ui.group.ViewHolder;
import com.tkteam.reading.utils.ProgressDialogHolder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.greenrobot.event.EventBus;

/**
 * Created by khiemvx on 5/27/15.
 */
public class CommonAdapter<T extends Group> extends BaseAdapter {
    private List<T> groupList = new ArrayList<>();
    private Context context;
    private boolean isShowDeleteButton = false;
    private boolean isShowEditButton = false;

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
        commonGroup.setDataToView(holder, convertView, isShowDeleteButton, isShowEditButton);
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
        ProgressDialogHolder.getInstance().dismissDialog();
    }

    public void onEventMainThread(ResetOneStoryEvent event) {
        if (!event.isCreateStory()) {
            List<Story> storyList = new ArrayList<>();
            Cursor c = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getReadableDatabase().rawQuery("SELECT * FROM  story WHERE id = ?", new String[]{event.getStoryId()});
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    storyList.add(StoryService.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).convertDataToObject(c, new Story()));
                } while (c.moveToNext());
                c.close();
            }
            Story story = storyList.get(0);
            story.setNumberQuestionAnswered("0");
            try {
                DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryDao().createOrUpdate(story);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ProgressGroup temp = null;
            for (T storyGroup : groupList)
                if (((ProgressGroup) storyGroup).getStoryId().equals(event.getStoryId())) {
                    temp = (ProgressGroup) storyGroup;
                    temp.setNumberAnswered("0");
                    break;
                }
        } else {
            List<StoryCreate> storyCreates = new ArrayList<>();
            Cursor c = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getReadableDatabase().rawQuery("SELECT * FROM  story_create WHERE id = ?", new String[]{event.getStoryId()});
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    storyCreates.add(StoryCreateService.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).convertDataToObject(c, new StoryCreate()));
                } while (c.moveToNext());
                c.close();
            }
            StoryCreate storyCreate = storyCreates.get(0);
            storyCreate.setNumberQuestionAnswered("0");
            try {
                DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryCreateDao().createOrUpdate(storyCreate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ProgressGroup temp = null;
            for (T storyGroup : groupList)
                if (((ProgressGroup) storyGroup).getStoryId().equals(event.getStoryId())) {
                    temp = (ProgressGroup) storyGroup;
                    temp.setNumberAnswered("0");
                    break;
                }
        }
        notifyDataSetChanged();
        ProgressDialogHolder.getInstance().dismissDialog();
    }

    public void onEventMainThread(ResetAllStoryEvent event) {
        if (!event.isCreateStory()) {
            List<Story> storyList = new ArrayList<>();
            try {
                storyList = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryDao().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (Story story : storyList) {
                story.setNumberQuestionAnswered("0");
                try {
                    DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryDao().createOrUpdate(story);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            for (T storyGroup : groupList) {
                ((ProgressGroup) storyGroup).setNumberAnswered("0");
            }
        } else {
            List<StoryCreate> storyCreateList = new ArrayList<>();
            try {
                storyCreateList = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryCreateDao().queryForAll();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for (StoryCreate storyCreate : storyCreateList) {
                storyCreate.setNumberQuestionAnswered("0");
                try {
                    DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryCreateDao().createOrUpdate(storyCreate);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            for (T storyGroup : groupList) {
                ((ProgressGroup) storyGroup).setNumberAnswered("0");
            }
        }
        notifyDataSetChanged();
        ProgressDialogHolder.getInstance().dismissDialog();
    }

    public void onEventMainThread(DeleteUserEvent event) {
        try {
            DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getUserDao().deleteById(UUID.fromString(event.getUserId()));
        } catch (SQLException e) {
            Log.e("Common Adapter", "Can't delete this sotry");
        }
        UserGroup temp = null;
        for (T userGroup : groupList)
            if (((UserGroup) userGroup).getUserId().equals(event.getUserId())) {
                temp = (UserGroup) userGroup;
                break;
            }
        if (temp != null)
            groupList.remove(temp);
        notifyDataSetChanged();
        ProgressDialogHolder.getInstance().dismissDialog();
    }

    public void onEventMainThread(AddUserEvent event) {
        try {
            User user = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getUserDao().queryForId(UUID.fromString(event.getUserId()));
            if (user != null) {
                UserGroup temp = new UserGroup();
                temp.setUserName(user.getName());
                temp.setAvatarUrl("file://" + user.getAvatarUrl());
                temp.setUserId(user.getId().toString());
                groupList.add((T) temp);
            }
            notifyDataSetChanged();
        } catch (SQLException e) {
            Log.e("Common Adapter", "Can't delete this sotry");
        }
        ProgressDialogHolder.getInstance().dismissDialog();
    }

    public void onEventMainThread(SetActiveUserEvent event) {
        try {
            List<User> userList = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getUserDao().queryForAll();
            for (User user : userList) {
                user.setCurrentUser(false);
                DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getUserDao().update(user);
            }
            User user = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getUserDao().queryForId(event.getUserId());
            user.setCurrentUser(true);
            DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getUserDao().update(user);
            groupList.clear();
            groupList = (List<T>) UserGroup.convertFromUser(UserService.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).findAll());
            notifyDataSetChanged();
        } catch (SQLException e) {
            Log.e("Common Adapter", "Can't delete this sotry");
        }
        ProgressDialogHolder.getInstance().dismissDialog();
    }

    public void onEventMainThread(EditUserRespontEvent event) {
        try {
            User user = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getUserDao().queryForId(UUID.fromString(event.getUserId()));
            UserGroup temp;
            for (T userGroup : groupList)
                if (((UserGroup) userGroup).getUserId().equals(event.getUserId())) {
                    temp = (UserGroup) userGroup;
                    temp.setUserName(user.getName());
                    temp.setAvatarUrl("file://" + user.getAvatarUrl());
                    break;
                }
            notifyDataSetChanged();
        } catch (SQLException e) {
            Log.e("Common Adapter", "Can't delete this sotry");
        }
        ProgressDialogHolder.getInstance().dismissDialog();
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

    public boolean isShowEditButton() {
        return isShowEditButton;
    }

    public void setShowEditButton(boolean isShowEditButton) {
        this.isShowEditButton = isShowEditButton;
    }
}
