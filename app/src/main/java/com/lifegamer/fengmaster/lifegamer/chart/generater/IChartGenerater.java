package com.lifegamer.fengmaster.lifegamer.chart.generater;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.LineDataSet;

/**
 * 图表数据生成接口
 * Created by FengMaster on 19/02/19.
 */
public interface IChartGenerater {

    /**
     * 生成图表数据集
     * @return
     */
    LineDataSet generateChartDate();


}
