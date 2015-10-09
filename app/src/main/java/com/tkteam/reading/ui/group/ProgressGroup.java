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
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.dao.entites.StoryCreate;
import com.tkteam.reading.ui.event.ResetOneStoryEvent;
import com.tkteam.reading.utils.ProgressDialogHolder;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Khiemvx on 8/14/2015.
 */
public class ProgressGroup extends Group {
    static boolean isCreateStory = false;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    private String storyName;
    private String numberAnswered;
    private String storyUrl;
    private String storyId;

    public ProgressGroup() {
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

    public static List<ProgressGroup> convertFromStorySystem(List<Story> storySystem) {
        List<ProgressGroup> progressGroups = new ArrayList<>();
        for (Story story : storySystem) {
            ProgressGroup progressGroup = new ProgressGroup();
            progressGroup.setStoryName(story.getTitle());
            progressGroup.setNumberAnswered(story.getNumberQuestionAnswered());
            progressGroup.setStoryUrl("assets://images/" + story.getThumb_image());
            progressGroup.setStoryId(story.getId());
            progressGroups.add(progressGroup);
        }
        isCreateStory = false;
        return progressGroups;
    }

    public static List<ProgressGroup> convertFromStoryCreate(List<StoryCreate> storyCreates) {
        List<ProgressGroup> progressGroups = new ArrayList<>();
        for (StoryCreate storyCreate : storyCreates) {
            ProgressGroup progressGroup = new ProgressGroup();
            progressGroup.setStoryName(storyCreate.getTitle());
            progressGroup.setNumberAnswered(storyCreate.getNumberQuestionAnswered());
            progressGroup.setStoryUrl("file://" + storyCreate.getThumb_image());
            progressGroup.setStoryId(storyCreate.getId().toString());
            progressGroups.add(progressGroup);
        }
        isCreateStory = true;
        return progressGroups;
    }

    @Override
    public int getLayout() {
        return R.layout.list_view_progress_item;
    }

    @Override
    public void findById(ViewHolder viewHolder, View view) {
        ImageView ivStory = (ImageView) view.findViewById(R.id.ivStory);
        ImageView ivReset = (ImageView) view.findViewById(R.id.ivReset);
        TextView tvStoryName = (TextView) view.findViewById(R.id.tvStoryName);
        TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        viewHolder.addView(ivStory);
        viewHolder.addView(tvStoryName);
        viewHolder.addView(tvDescription);
        viewHolder.addView(ivReset);
    }

    @Override
    public void setDataToView(ViewHolder viewHolder, View view, boolean isShowDeleteButton, boolean isShowEditButton) {
        ImageView ivStory = (ImageView) viewHolder.getView(R.id.ivStory);
        ImageView ivReset = (ImageView) viewHolder.getView(R.id.ivReset);
        TextView tvStoryName = (TextView) viewHolder.getView(R.id.tvStoryName);
        TextView tvDescription = (TextView) viewHolder.getView(R.id.tvDescription);
        tvStoryName.setText(storyName);
        tvDescription.setText("Multiple Choice: " + numberAnswered + "/7");
        ImageLoader.getInstance().displayImage(storyUrl, ivStory, options);
        ivReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialogHolder.getInstance().showDialogWithoutMessage();
                EventBus.getDefault().post(new ResetOneStoryEvent(storyId, isCreateStory));
            }
        });
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getNumberAnswered() {
        return numberAnswered;
    }

    public void setNumberAnswered(String numberAnswered) {
        this.numberAnswered = numberAnswered;
    }

    public String getStoryUrl() {
        return storyUrl;
    }

    public void setStoryUrl(String storyUrl) {
        this.storyUrl = storyUrl;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }
}
