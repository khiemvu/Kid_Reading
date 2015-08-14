package com.tkteam.reading.ui.group;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.tkteam.reading.dao.entites.StoryCreate;
import com.tkteam.reading.ui.event.DeleteStoryEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.greenrobot.event.EventBus;

/**
 * Created by Khiemvx on 6/6/2015.
 */
public class ManageStoryGroup extends Group {

    DisplayImageOptions options;
    ImageLoader imageLoader;
    private String storyName;
    private String description;
    private String storyUrl;
    private UUID storyId;

    public ManageStoryGroup() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
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

    public static List<ManageStoryGroup> convertFromStory(List<StoryCreate> storyCreateList) {
        List<ManageStoryGroup> storyGroups = new ArrayList<>();
        for (StoryCreate storyCreate : storyCreateList) {
            ManageStoryGroup userGroup = new ManageStoryGroup();
            userGroup.setStoryName(storyCreate.getTitle());
            userGroup.setDescription(storyCreate.getContent());
            userGroup.setStoryUrl(storyCreate.getThumb_image());
            userGroup.setStoryId(storyCreate.getId());
            storyGroups.add(userGroup);
        }
        return storyGroups;
    }

    @Override
    public int getLayout() {
        return R.layout.list_view_manager_story_item;
    }

    @Override
    public void findById(ViewHolder viewHolder, View view) {
        ImageView ivStory = (ImageView) view.findViewById(R.id.ivStory);
        ImageView ivDelete = (ImageView) view.findViewById(R.id.ivDelete);
        TextView tvStoryName = (TextView) view.findViewById(R.id.tvStoryName);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        viewHolder.addView(ivStory);
        viewHolder.addView(tvStoryName);
        viewHolder.addView(tvDescription);
        viewHolder.addView(ivDelete);
    }

    @Override
    public void setDataToView(ViewHolder viewHolder, View view, boolean isShowDeleteButton) {
        ImageView ivStory = (ImageView) viewHolder.getView(R.id.ivStory);
        ImageView ivDelete = (ImageView) viewHolder.getView(R.id.ivDelete);
        TextView tvStoryName = (TextView) viewHolder.getView(R.id.tvStoryName);
        TextView tvDescription = (TextView) viewHolder.getView(R.id.tvDescription);

        ivDelete.setVisibility(isShowDeleteButton ? View.VISIBLE : View.GONE);
        tvStoryName.setText(storyName);
        tvDescription.setText(description);
        Bitmap bitmap = BitmapFactory.decodeFile(storyUrl);
        ivStory.setImageBitmap(bitmap);
        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteStoryEvent(storyId));
            }
        });
//        ImageLoader.getInstance().displayImage(storyUrl, ivStory);
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }

    public UUID getStoryId() {
        return storyId;
    }

    public void setStoryId(UUID storyId) {
        this.storyId = storyId;
    }
}
