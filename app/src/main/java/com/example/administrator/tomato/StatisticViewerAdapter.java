package com.example.administrator.tomato;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StatisticViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String LOG_TAG = "StatisticViewerAdapter";
    private Context mContext;
    private DBHelper mDataBase;
    private LinearLayoutManager llm;
//    private String [] titles;
    public enum ITEM_TYPE {
        LINERCHART,
        PIECHART
    }
    private  int chartNumber;

    //折线
    public static class LineChartViewHolder extends RecyclerView.ViewHolder {
//        public int type = 0;
        protected TextView textView;
        protected LineChart lineChart;
        public LineChartViewHolder(@NonNull View itemView) {
            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.test_text);
            lineChart = (LineChart) itemView.findViewById(R.id.line_chart);
//            textView.setText("test");
        }
    }

    //扇形
    public static class PieChartViewHolder extends RecyclerView.ViewHolder {
//        public int type = 1;
        protected TextView textView;
        protected PieChart pieChart;
        public PieChartViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.pie_chart_text);
            pieChart = (PieChart) itemView.findViewById(R.id.piechart);
        }
    }

//    private int position;
//    private LayoutInflater mLayoutInflater;

    public StatisticViewerAdapter(Context context,LinearLayoutManager linearLayoutManager)
    {
        super();
        mContext = context;
        llm = linearLayoutManager;
        chartNumber = 0;
        mDataBase = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int chartType) {

        View view;
        if (chartType == ITEM_TYPE.LINERCHART.ordinal())
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.linechart_card_view, viewGroup,false
            );
            return new LineChartViewHolder(view);
        }
        else
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.piechart_card_view, viewGroup,false
            );
            return new PieChartViewHolder(view);
        }

    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
//        chartViewHolder.charType.setText("month");

//        if (holder == null)
//            System.out.println("Right!");
        FetchDataToGraph fetchDataToGraph = new FetchDataToGraph(mContext);
        if (holder instanceof LineChartViewHolder) {

            LineChartViewHolder lineChartViewHolder = (LineChartViewHolder) holder;
//            String string ="testtest"+Integer.toString(mDataBase.getTaskCount());
//            String string = "testtest" + fetchDataToGraph.test();
//            lineChartViewHolder.textView.setText(string);
//            lineChartViewHolder.lineChart
            LineChart lineChart = lineChartViewHolder.lineChart;

            //创建描述信息
//            Description description = new Description();
////            description.setText("最近一月的");
//            description.setTextColor(Color.RED);
//            description.setTextSize(20);
//            lineChart.setDescription(description);//设置图表描述信息
//            lineChart.setNoDataText("没有数据熬");//没有数据时显示的文字
//            lineChart.setNoDataTextColor(Color.BLUE);//没有数据时显示文字的颜色
            lineChart.setDrawGridBackground(false);//chart 绘图区后面的背景矩形将绘制
            lineChart.setDrawBorders(false);//禁止绘制图表边框的线
            //lineChart.setBorderColor(); //设置 chart 边框线的颜色。
            //lineChart.setBorderWidth(); //设置 chart 边界线的宽度，单位 dp。
            //lineChart.setLogEnabled(true);//打印日志
            //lineChart.notifyDataSetChanged();//刷新数据
            //lineChart.invalidate();//重绘

            ArrayList<Entry> values1 = new ArrayList<>();
            ArrayList<Entry> values2 = new ArrayList<>();

            values1.add(new Entry(4, 10));
            values1.add(new Entry(6, 15));
            values1.add(new Entry(9, 20));
            values1.add(new Entry(12, 5));
            values1.add(new Entry(15, 30));

//            values2.add(new Entry(3, 110));
//            values2.add(new Entry(6, 115));
//            values2.add(new Entry(9, 130));
//            values2.add(new Entry(12, 85));
//            values2.add(new Entry(15, 90));


            LineDataSet set1;
//            LineDataSet set2;


            if (lineChart.getData() != null &&
                    lineChart.getData().getDataSetCount() > 0) {

                set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
                set1.setValues(values1);
//                set2 = (LineDataSet) lineChart.getData().getDataSetByIndex(1);
//                set2.setValues(values2);

                lineChart.getData().notifyDataChanged();
                lineChart.notifyDataSetChanged();
            } else {

                set1 = new LineDataSet(values1, "任务汇总");
                set1.setColor(Color.BLACK);
                set1.setCircleColor(Color.BLACK);
                set1.setLineWidth(1f);
                set1.setCircleRadius(3f);
                set1.enableDashedHighlightLine(10f, 5f, 0f);
                set1.setHighlightLineWidth(2f);
                set1.setHighlightEnabled(true);
                set1.setHighLightColor(Color.RED);
                set1.setValueTextSize(9f);
                set1.setDrawFilled(false);


                final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
                set1.setValueFormatter(new IValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                        return mFormat.format(value);
                    }
                });


//                set2 = new LineDataSet(values2, "测试数据2");
//                set2.setColor(Color.GRAY);
//                set2.setCircleColor(Color.GRAY);
//                set2.setLineWidth(1f);
//                set2.setCircleRadius(3f);
//                set2.setValueTextSize(10f);


                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);
//                dataSets.add(set2);

                LineData data = new LineData(dataSets);

                lineChart.setData(data);

                lineChart.invalidate();

            }
        }
        else if(holder instanceof PieChartViewHolder)
        {
//            PieChartViewHolder pieChartViewHolder = (PieChartViewHolder) holder;
//            String string ="testtesttest";
//            pieChartViewHolder.textView.setText(string);
            FetchDataToGraph fetchDataToGraph1 = new FetchDataToGraph(mContext);
            PieChartViewHolder pieChartViewHolder = (PieChartViewHolder) holder;
            PieData piedata = fetchDataToGraph.getDataToPiechart();
            Description description = new Description();
            description.setEnabled(false);
            pieChartViewHolder.pieChart.setDescription(description);
            pieChartViewHolder.pieChart.setEntryLabelTextSize(20);
            pieChartViewHolder.pieChart.setEntryLabelColor(Color.BLACK);
            pieChartViewHolder.pieChart.setData(piedata);
            pieChartViewHolder.pieChart.postInvalidate();
        }


    }

    @Override
    public int getItemCount() {
//        return chartNumber;
        return 2;
    }


    public int getItemViewType(int position)
    {
        return position % 2 == 0 ? ITEM_TYPE.LINERCHART.ordinal() : ITEM_TYPE.PIECHART.ordinal();
    }

//    private int chartType;

    public void addChart()
    {
        chartNumber += 1;
        notifyItemInserted(getItemCount()-1);
//        llm.scrollToPosition(getItemCount()-1);
    }
}
