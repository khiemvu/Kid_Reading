package com.tkteam.reading.ui.fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.dao.DatabaseHelper;
import com.tkteam.reading.dao.entites.Question;
import com.tkteam.reading.service.QuestionSerVice;
import com.tkteam.reading.ui.adapter.ReadStoryAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Khiemvx on 6/21/2015.
 */
public class ReadStoryFragment extends BaseFragment {
    @InjectView(R.id.ivBack)
    ImageView ivBack;
    @InjectView(R.id.tvTitleStory)
    TextView tvTitleStory;
    @InjectView(R.id.ivStory)
    ImageView ivStory;
    @InjectView(R.id.tvStoryContent)
    TextView tvStoryContent;
    @InjectView(R.id.viewPager)
    ViewPager viewPager;
    @InjectView(R.id.indicator)
    CirclePageIndicator mIndicator;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    List<Question> questions;
    private String storyId, storyName, storyContent, storyImage;

    public ReadStoryFragment() {
    }

    public ReadStoryFragment(String storyId, String storyName, String storyContent, String storyImage) {
        this.storyId = storyId;
        this.storyName = storyName;
        this.storyContent = storyContent;
        this.storyImage = storyImage;
    }

    @Override
    public int getLayout() {
        return R.layout.read_story_fragment;
    }

    @Override
    public void setupView() {
        tvTitleStory.setText(storyName);
        tvStoryContent.setText(storyContent);
        tvTitleStory.setText(storyName);
        questions = getDataFromDataBase();
        showImage();
        ReadStoryAdapter readStoryAdapter = new ReadStoryAdapter(getChildFragmentManager(), questions);
        viewPager.setAdapter(readStoryAdapter);
        viewPager.setOffscreenPageLimit(questions.size());
        mIndicator.setViewPager(viewPager);
    }

    private List<Question> getDataFromDataBase() {
        List<Question> questions = new ArrayList<>();
        Cursor c = DatabaseHelper.getInstance(getActivity()).getReadableDatabase().rawQuery("SELECT * FROM  questions WHERE story_id = ?", new String[]{storyId});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                questions.add(QuestionSerVice.getInstance(getActivity()).convertDataToObject(c, new Question()));
            } while (c.moveToNext());
            c.close();
        }
        return questions;
    }

    private void showImage() {
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

        ImageLoader.getInstance().displayImage(storyImage, ivStory, options);
    }

    @OnClick(R.id.ivBack)
    public void onBack() {
        getActivity().onBackPressed();
    }
}
