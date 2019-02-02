package com.lifegamer.fengmaster.lifegamer.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lifegamer.fengmaster.lifegamer.Game;
import com.lifegamer.fengmaster.lifegamer.R;
import com.lifegamer.fengmaster.lifegamer.model.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 统计页面
 */
public class StatisticsFragment extends Fragment {


    private Date startDate;

    private Date endDate=new Date();


    @BindView(R.id.lc_statistics_chart)
    public LineChart lineChart;

    public StatisticsFragment() {
        // Required empty public constructor
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-30);
        startDate=calendar.getTime();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this,view);
        initView();
        initDataSet();
        return view;
    }


    private void initView(){
        //不显示网格线
        lineChart.setDrawGridBackground(false);
        //可拖动
        lineChart.setDragEnabled(true);
        //可触摸
        lineChart.setTouchEnabled(true);

        //X轴
        XAxis xAxis = lineChart.getXAxis();
        //底部显示x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        lineChart.getAxisLeft().setAxisMinimum(0f);

        lineChart.animateX(1500);
        lineChart.animateX(2500);

        lineChart.getAxisRight().setAxisMinimum(0f);
        lineChart.getAxisRight().setAxisMaximum(20f);

//        lineChart.getAxisRight().setAxisLineWidth(500f);

        SimpleDateFormat dateFormat=new SimpleDateFormat("MM-dd");


        xAxis.setValueFormatter((value, axis) -> dateFormat.format(Double.valueOf(value).longValue()));

    }


    private void initDataSet(){

        List<Log> taskLogs = Game.getInstance().getLogManager().getAllLog(Log.TYPE.TASK);

        List<Entry> successTask=new ArrayList<>();
        List<Entry> failTask=new ArrayList<>();

        long l = endDate.getTime() - startDate.getTime();

        long day = l / (1000 * 60 * 60 * 24);

        for (long i=0;i<=day;i++){
            Entry entry = new Entry();
            entry.setY(0);
            entry.setX((startDate.getTime()+i*1000*60*60*24));
            successTask.add(Long.valueOf(i).intValue(),entry);
        }

        for (long i=0;i<=day;i++){
            Entry entry = new Entry();
            entry.setY(0);
            entry.setX(startDate.getTime()+i*1000*60*60*24);
            failTask.add(Long.valueOf(i).intValue(),entry);
        }
        for (Log taskLog : taskLogs) {
            int dd = (int) ((taskLog.getLogTime().getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
            if (taskLog.getAction().equals(Log.ACTION.FINISH)){
                Entry entry1 = successTask.get(dd);
                entry1.setY(entry1.getY()+1);
            }else if (taskLog.getAction().equals(Log.ACTION.FAIL)){
                Entry entry1 = failTask.get(dd);
                entry1.setY(entry1.getY()+1);
            }
        }

        LineDataSet lineDataSetSuccess=new LineDataSet(successTask,"任务完成情况");
        lineDataSetSuccess.setColor(Color.BLUE);
        lineDataSetSuccess.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSetSuccess.setValueTextColor(Color.WHITE);
        lineDataSetSuccess.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return Double.valueOf(value).intValue()+"";
            }
        });
        LineDataSet lineDataSetFail=new LineDataSet(failTask,"任务失败情况");
        lineDataSetFail.setColor(Color.RED);
        lineDataSetFail.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSetFail.setValueTextColor(Color.WHITE);
        lineDataSetFail.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return Double.valueOf(value).intValue()+"";
            }
        });

//        lineDataSet.setLineWidth(3f);
//        lineDataSet.setDrawFilled(true);
//        lineDataSet.setFormSize(15f);
        LineData lineData=new LineData();
        lineData.addDataSet(lineDataSetFail);
        lineData.addDataSet(lineDataSetSuccess);
//        lineChart.setExtraTopOffset(100f);
        lineChart.setData(lineData);
    }


}
