package com.tkteam.reading.ui.group;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.R;
import com.tkteam.reading.dao.entites.User;
import com.tkteam.reading.ui.event.DeleteUserEvent;
import com.tkteam.reading.ui.event.RequestEditUserEvent;
import com.tkteam.reading.ui.event.SetActiveUserEvent;
import com.tkteam.reading.utils.ProgressDialogHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.greenrobot.event.EventBus;

/**
 * Created by Trung on 6/2/2015.
 */
public class UserGroup extends Group {
    DisplayImageOptions options;
    ImageLoader imageLoader;
    private String userName;
    private String avatarUrl;
    private String userId;
    private boolean isUserActive;

    public UserGroup() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(false)
                .cacheOnDisc(false)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ApplicationStateHolder.getInstance().getMyActivity())
                .memoryCacheExtraOptions(480, 800)
                .threadPoolSize(5)
                .threadPriority(Thread.MIN_PRIORITY + 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    public static List<UserGroup> convertFromUser(List<User> userList) {
        List<UserGroup> userGroups = new ArrayList<>();
        for (User user : userList) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUserName(user.getName());
            userGroup.setAvatarUrl("file://" + user.getAvatarUrl());
            userGroup.setUserId(user.getId().toString());
            userGroup.setUserActive(user.isCurrentUser());
            userGroups.add(userGroup);
        }
        return userGroups;
    }

    @Override
    public int getLayout() {
        return R.layout.user_grid_item;
    }

    @Override
    public void findById(ViewHolder viewHolder, View view) {
        ImageView imageView = (ImageView) view.findViewById(R.id.user_grid_item_ivAvatar);
        ImageView ivUserActive = (ImageView) view.findViewById(R.id.user_grid_item_ivUserActive);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.user_grid_item_ivDelete);
        ImageView ivEdit = (ImageView) view.findViewById(R.id.user_grid_item_ivEdit);
        TextView tvName = (TextView) view.findViewById(R.id.user_grid_item_tvName);
        viewHolder.addView(ivUserActive);
        viewHolder.addView(imageView);
        viewHolder.addView(ivDelete);
        viewHolder.addView(ivEdit);
        viewHolder.addView(tvName);
    }

    @Override
    public void setDataToView(ViewHolder viewHolder, View view, boolean isShowDeleteButton, boolean isShowEditButton) {
        ImageView imageView = (ImageView) viewHolder.getView(R.id.user_grid_item_ivAvatar);
        TextView tvName = (TextView) viewHolder.getView(R.id.user_grid_item_tvName);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.user_grid_item_ivDelete);
        ImageView ivEdit = (ImageView) view.findViewById(R.id.user_grid_item_ivEdit);
        ivEdit.setVisibility(isShowEditButton ? View.VISIBLE : View.GONE);
        final ImageView ivUserActive = (ImageView) view.findViewById(R.id.user_grid_item_ivUserActive);
        ivDelete.setVisibility(isShowDeleteButton ? View.VISIBLE : View.GONE);
        if (!avatarUrl.contains("null"))
            ImageLoader.getInstance().displayImage(avatarUrl, imageView, options);
        tvName.setText(userName);
        ivUserActive.setVisibility(isUserActive ? View.VISIBLE : View.GONE);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogHolder.getInstance().showDialogWithoutMessage();
                EventBus.getDefault().post(new SetActiveUserEvent(UUID.fromString(userId)));
                ApplicationStateHolder.getInstance().setUserActiveId(UUID.fromString(userId));
                ApplicationStateHolder.getInstance().setUserActiveName(userName);
            }
        });
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogHolder.getInstance().showDialogWithoutMessage();
                EventBus.getDefault().post(new DeleteUserEvent(userId));
            }
        });
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogHolder.getInstance().showDialogWithoutMessage();
                EventBus.getDefault().post(new RequestEditUserEvent(userId));
            }
        });
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isUserActive() {
        return isUserActive;
    }

    public void setUserActive(boolean isUserActive) {
        this.isUserActive = isUserActive;
    }
}
