package com.tkteam.reading.ui.fragment;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
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
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.dao.DatabaseHelper;
import com.tkteam.reading.dao.entites.Question;
import com.tkteam.reading.dao.entites.QuestionCreate;
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.dao.entites.StoryCreate;
import com.tkteam.reading.service.QuestionCreateService;
import com.tkteam.reading.service.QuestionSerVice;
import com.tkteam.reading.service.StoryCreateService;
import com.tkteam.reading.service.StoryService;
import com.tkteam.reading.ui.adapter.ReadStoryCreateAdapter;
import com.tkteam.reading.ui.adapter.ReadStorySystemAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Khiemvx on 6/21/2015.
 */
public class ReadStoryFragment extends BaseFragment {
    static int numberAnswered;
    static int numberAnsweredRight;
    TextToSpeech textToSpeech;
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
    @InjectView(R.id.ivSpeak)
    ImageView ivSpeak;
    @InjectView(R.id.btSubmit)
    ImageView btSubmit;
    DisplayImageOptions options;
    ImageLoader imageLoader;
    List<Question> questions;
    List<QuestionCreate> questionCreates;
    private String storyId, storyName, storyContent, storyImage;
    private boolean isStoryCreate;

    public ReadStoryFragment() {
    }

    public ReadStoryFragment(String storyId, String storyName, String storyContent, String storyImage, boolean isStoryCreate) {
        this.storyId = storyId;
        this.storyName = storyName;
        this.storyContent = storyContent;
        this.storyImage = storyImage;
        this.isStoryCreate = isStoryCreate;
    }

    @Override
    public int getLayout() {
        return R.layout.read_story_fragment;
    }

    @Override
    public void setupView() {
        numberAnswered = 0;
        numberAnsweredRight = 0;
        tvTitleStory.setText(storyName);
        tvStoryContent.setText(storyContent);
        tvTitleStory.setText(storyName);
        showImage();
        if (!isStoryCreate)
            prepareDataSystemForBindOnView();
        else {
            prepareDataCreateForBindOnView();
        }
        textToSpeech = new TextToSpeech(ApplicationStateHolder.getInstance().getMyActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });
        textToSpeech.speak(storyName, TextToSpeech.QUEUE_FLUSH, null);
        ivSpeak.setVisibility(ApplicationStateHolder.getInstance().isReadStory() ? View.VISIBLE : View.GONE);
    }

    private void prepareDataSystemForBindOnView() {
        numberAnsweredRight = 0;
        questions = getQuestionSystemFromDB();
        ReadStorySystemAdapter readStoryAdapter = new ReadStorySystemAdapter(getChildFragmentManager(), questions);
        viewPager.setAdapter(readStoryAdapter);
        viewPager.setOffscreenPageLimit(questions.size());
        mIndicator.setViewPager(viewPager);
    }

    private void prepareDataCreateForBindOnView() {
        numberAnsweredRight = 0;
        questionCreates = getQuestionCreateFromDB();
        ReadStoryCreateAdapter readStoryCreateAdapter = new ReadStoryCreateAdapter(getChildFragmentManager(), questionCreates);
        viewPager.setAdapter(readStoryCreateAdapter);
        viewPager.setOffscreenPageLimit(questionCreates.size());
        mIndicator.setViewPager(viewPager);
    }

    private List<Question> getQuestionSystemFromDB() {
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

    private List<QuestionCreate> getQuestionCreateFromDB() {
        List<QuestionCreate> questions = new ArrayList<>();
        Cursor c = DatabaseHelper.getInstance(getActivity()).getReadableDatabase().rawQuery("SELECT * FROM  question_create WHERE storyId = ?", new String[]{storyId});
        if (c.getCount() > 0) {
            c.moveToFirst();
            do {
                questions.add(QuestionCreateService.getInstance(getActivity()).convertDataToObject(c, new QuestionCreate()));
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

    @OnClick(R.id.btSubmit)
    public void onSubmit() {
        if (!isStoryCreate) {
            List<Story> storyList = new ArrayList<>();
            Cursor c = DatabaseHelper.getInstance(getActivity()).getReadableDatabase().rawQuery("SELECT * FROM  story WHERE id = ?", new String[]{storyId});
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    storyList.add(StoryService.getInstance(getActivity()).convertDataToObject(c, new Story()));
                } while (c.moveToNext());
                c.close();
            }
            Story story = storyList.get(0);
            story.setNumberQuestionAnswered(String.valueOf(numberAnswered + 1));
            story.setNumberAnswerCorrect(String.valueOf(numberAnsweredRight));
            try {
                DatabaseHelper.getInstance(getActivity()).getStoryDao().createOrUpdate(story);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            List<StoryCreate> storyCreates = new ArrayList<>();
            Cursor c = DatabaseHelper.getInstance(getActivity()).getReadableDatabase().rawQuery("SELECT * FROM  story_create WHERE id = ?", new String[]{storyId});
            if (c.getCount() > 0) {
                c.moveToFirst();
                do {
                    storyCreates.add(StoryCreateService.getInstance(getActivity()).convertDataToObject(c, new StoryCreate()));
                } while (c.moveToNext());
                c.close();
            }
            StoryCreate storyCreate = storyCreates.get(0);
            storyCreate.setNumberQuestionAnswered(String.valueOf(numberAnswered + 1));
            storyCreate.setNumberAnsweredCorrect(String.valueOf(numberAnsweredRight));
            try {
                DatabaseHelper.getInstance(getActivity()).getStoryCreateDao().createOrUpdate(storyCreate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        getActivity().onBackPressed();
    }

    @OnClick(R.id.ivSpeak)
    public void onSpeak() {
        textToSpeech.speak(storyContent, TextToSpeech.QUEUE_FLUSH, null);
    }

    @OnClick(R.id.ivBack)
    public void onBack() {
        textToSpeech.stop();
        getActivity().onBackPressed();
    }
}
