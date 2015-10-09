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
import com.tkteam.reading.base.event.ChangedFragmentEvent;
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.dao.entites.StoryCreate;
import com.tkteam.reading.ui.fragment.ReadStoryFragment;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Khiemvx on 6/20/2015.
 */
public class LessonGroup extends Group {
    DisplayImageOptions options;
    ImageLoader imageLoader;
    private String storyName;
    private String storyImage;
    private String storyId;
    private String storyContent;

    public LessonGroup() {
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

    public static List<LessonGroup> convertFromStory(List<Story> storyList) {
        List<LessonGroup> storyGroups = new ArrayList<>();
        for (Story story : storyList) {
            LessonGroup lessonGroup = new LessonGroup();
            lessonGroup.setStoryName(story.getTitle());
            lessonGroup.setStoryImage("assets://images/" + story.getThumb_image());
            lessonGroup.setStoryId(story.getId());
            lessonGroup.setStoryContent(story.getContent());
            storyGroups.add(lessonGroup);
        }
        return storyGroups;
    }

    public static List<LessonGroup> convertFromStoryCreate(List<StoryCreate> storyCreateList) {
        List<LessonGroup> storyGroups = new ArrayList<>();
        for (StoryCreate story : storyCreateList) {
            LessonGroup lessonGroup = new LessonGroup();
            lessonGroup.setStoryName(story.getTitle());
            lessonGroup.setStoryImage("file://" + story.getThumb_image());
            lessonGroup.setStoryId(story.getId().toString());
            lessonGroup.setStoryContent(story.getContent());
            storyGroups.add(lessonGroup);
        }
        return storyGroups;
    }

    @Override
    public int getLayout() {
        return R.layout.start_fragment_grid_veiw_item;
    }

    @Override
    public void findById(ViewHolder viewHolder, View view) {
        ImageView ivStory = (ImageView) view.findViewById(R.id.ivItem);
        TextView tvStoryName = (TextView) view.findViewById(R.id.tvItem);
        viewHolder.addView(ivStory);
        viewHolder.addView(tvStoryName);
    }

    @Override
    public void setDataToView(ViewHolder viewHolder, View view, boolean isShowDeleteButton, boolean isShowEditButton) {
        ImageView ivStory = (ImageView) viewHolder.getView(R.id.ivItem);
        TextView tvStoryName = (TextView) viewHolder.getView(R.id.tvItem);
        tvStoryName.setText(storyName);
        ImageLoader.getInstance().displayImage(storyImage, ivStory, options);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isStoryCreate = storyImage.contains("file");
                EventBus.getDefault().post(new ChangedFragmentEvent(new ReadStoryFragment(storyId, storyName, storyContent, storyImage, isStoryCreate)));
            }
        });
    }

    public String getStoryImage() {
        return storyImage;
    }

    public void setStoryImage(String storyImage) {
        this.storyImage = storyImage;
    }

    public String getStoryName() {
        return storyName;
    }

    public void setStoryName(String storyName) {
        this.storyName = storyName;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryContent() {
        return storyContent;
    }

    public void setStoryContent(String storyContent) {
        this.storyContent = storyContent;
    }
}
