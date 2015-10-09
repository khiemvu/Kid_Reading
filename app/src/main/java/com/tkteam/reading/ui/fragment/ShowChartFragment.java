package com.tkteam.reading.ui.fragment;

import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.tkteam.reading.ApplicationStateHolder;
import com.tkteam.reading.R;
import com.tkteam.reading.base.BaseFragment;
import com.tkteam.reading.dao.DatabaseHelper;
import com.tkteam.reading.dao.entites.Story;
import com.tkteam.reading.dao.entites.StoryCreate;
import com.tkteam.reading.ui.customview.MyValueFormatter;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Khiemvx on 8/25/2015.
 */
public class ShowChartFragment extends BaseFragment {

    protected String[] mMonths;
    @InjectView(R.id.chart1)
    BarChart mChart;
    @InjectView(R.id.rbSystemLesson)
    RadioButton rbSystemLesson;
    @InjectView(R.id.rbCreateLesson)
    RadioButton rbCreateLesson;
    @InjectView(R.id.ivBack)
    ImageView ivBack;
    @InjectView(R.id.tvTitleScreen)
    TextView tvTitleScreen;
    @InjectView(R.id.ivReset)
    ImageView ivReset;
    boolean isCreateStory = false;

    @Override
    public void setupView() {
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        tvTitleScreen.setText(ApplicationStateHolder.getInstance().getUserActiveName() + "'s Graph");
        setupChart();
        prepareSystemDataAndBindOnView();
    }

    private void setupChart() {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setDescription("");
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mChart.setVisibleXRangeMaximum(10);
        mChart.setDoubleTapToZoomEnabled(false);
        mChart.zoom(8f, 0f, 30f, 0f);
        // scaling can now only be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        mChart.setAutoScaleMinMaxEnabled(true);
        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);
        // mChart.setDrawXLabels(false);
        mChart.setDrawGridBackground(true);
        // mChart.setDrawYLabels(false);
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setTextSize(15f);
        ValueFormatter custom = new MyValueFormatter();
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setValueFormatter(custom);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMaxValue(100);
        leftAxis.setTextSize(15f);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setLabelCount(8, false);
        rightAxis.setValueFormatter(custom);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMaxValue(100);
        rightAxis.setTextSize(15f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(35f);
        l.setXEntrySpace(10f);
    }

    @OnClick(R.id.rbSystemLesson)
    public void prepareSystemDataAndBindOnView() {
        isCreateStory = false;
        if (mChart.getData() != null)
            mChart.clear();
        List<Story> storyList = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<String>();
        try {
            storyList = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryDao().queryForAll();
            for (int count = 0; count < storyList.size(); count++) {
                xVals.add(String.valueOf(count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        int i = 0;
        for (Story story : storyList) {
            double percent = Double.parseDouble(story.getNumberAnsweredCorrect()) / 7 * 100;
            DecimalFormat df = new DecimalFormat("##");
            percent = Double.parseDouble(df.format(percent));
            yVals1.add(new BarEntry((float) percent, i));
            i++;
        }

        BarDataSet set1 = new BarDataSet(yVals1, "Stories");
        set1.setBarSpacePercent(35f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        // data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(15f);
        mChart.setData(data);
    }

    @OnClick(R.id.rbCreateLesson)
    public void prepareCreateDataAndBindOnView() {
        isCreateStory = true;
        if (mChart.getData() != null)
            mChart.clear();
        List<StoryCreate> storyCreates = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<String>();
        try {
            storyCreates = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryCreateDao().queryForAll();
            for (int count = 0; count < storyCreates.size(); count++) {
                xVals.add(String.valueOf(count));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        int i = 0;
        for (StoryCreate storyCreate : storyCreates) {
            double percent = Double.parseDouble(storyCreate.getNumberAnsweredCorrect()) / 7 * 100;
            yVals1.add(new BarEntry((float) percent, i));
            i++;
        }

        BarDataSet set1 = new BarDataSet(yVals1, "");
        set1.setBarSpacePercent(55f);

        ArrayList<BarDataSet> dataSets = new ArrayList<BarDataSet>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        // data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(15f);
        mChart.setData(data);
    }

    @OnClick(R.id.ivReset)
    public void onReset() {
        if (isCreateStory) {
            List<StoryCreate> storyCreates = new ArrayList<>();
            try {
                storyCreates = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryCreateDao().queryForAll();
                for (StoryCreate storyCreate : storyCreates) {
                    storyCreate.setNumberAnsweredCorrect("0");
                    DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryCreateDao().update(storyCreate);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            List<Story> storyList = new ArrayList<>();
            try {
                storyList = DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryDao().queryForAll();
                for (Story story : storyList) {
                    story.setNumberAnswerCorrect("0");
                    DatabaseHelper.getInstance(ApplicationStateHolder.getInstance().getMyActivity()).getStoryDao().update(story);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mChart.getData().clearValues();
    }

    @OnClick(R.id.ivBack)
    public void onBackPress() {
        getActivity().onBackPressed();
    }

    @Override
    public int getLayout() {
        return R.layout.chart_fragment;
    }


}
