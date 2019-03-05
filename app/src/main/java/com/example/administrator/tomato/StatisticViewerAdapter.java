package com.example.administrator.tomato;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;

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
            textView = (TextView) itemView.findViewById(R.id.test_text);
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
        if (holder instanceof LineChartViewHolder)
        {

            LineChartViewHolder lineChartViewHolder = (LineChartViewHolder) holder;
//            String string ="testtest"+Integer.toString(mDataBase.getTaskCount());
            String string ="testtest"+ fetchDataToGraph.test();
            lineChartViewHolder.textView.setText(string);
//            lineChartViewHolder.lineChart
            LineChart lineChart = lineChartViewHolder.lineChart;

            //这里对折线图操作

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
