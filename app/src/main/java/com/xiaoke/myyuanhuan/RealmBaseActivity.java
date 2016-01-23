package com.xiaoke.myyuanhuan;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Philipp Jahoda on 05/11/15.
 */
public abstract class RealmBaseActivity extends DemoBase {

    protected Realm mRealm;

    protected Typeface mTf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Realm.io Examples");
    }

    protected void setup(Chart<?> chart) {

        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        // no description text
        chart.setDescription("");
        chart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        chart.setTouchEnabled(true);

        if (chart instanceof BarLineChartBase) {

            BarLineChartBase mChart = (BarLineChartBase) chart;

            mChart.setDrawGridBackground(false);

            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            mChart.setPinchZoom(false);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
            leftAxis.setStartAtZero(false);
            leftAxis.setTypeface(mTf);
            leftAxis.setTextSize(8f);
            leftAxis.setTextColor(Color.DKGRAY);
            leftAxis.setValueFormatter(new PercentFormatter());

            XAxis xAxis = mChart.getXAxis();
            xAxis.setTypeface(mTf);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(8f);
            xAxis.setTextColor(Color.DKGRAY);

            mChart.getAxisRight().setEnabled(false);
        }
    }

    protected void styleData(ChartData data) {
        data.setValueTypeface(mTf);
        data.setValueTextSize(8f);
        data.setValueTextColor(Color.DKGRAY);
        data.setValueFormatter(new PercentFormatter());
    }

    @Override
    protected void onResume() {
        super.onResume();

        RealmConfiguration config = new RealmConfiguration.Builder(this)
                .name("myrealm.realm")
                .build();

        Realm.deleteRealm(config);

        Realm.setDefaultConfiguration(config);

        mRealm = Realm.getInstance(config);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRealm.close();
    }
    protected void writeToDBPie() {

        mRealm.beginTransaction();

        mRealm.clear(RealmDemoData.class);

        float value1 = 15f + (float) (Math.random() * 8f);
        float value2 = 15f + (float) (Math.random() * 8f);
        float value3 = 15f + (float) (Math.random() * 8f);
        float value4 = 15f + (float) (Math.random() * 8f);
        float value5 = 100f - value1 - value2 - value3 - value4;

        float[] values = new float[] { value1, value2, value3, value4, value5 };
        String[] xValues = new String[]{ "一般舆情", "黄色舆情", "橙色舆情", "蓝色舆情", "红色舆情"};

        for (int i = 0; i < values.length; i++) {
            RealmDemoData d = new RealmDemoData(values[i], i, xValues[i]);
            mRealm.copyToRealm(d);
        }

        mRealm.commitTransaction();
    }
}
