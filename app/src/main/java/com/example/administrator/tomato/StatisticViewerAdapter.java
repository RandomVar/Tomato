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
    public enum ITEM_TYPE {
        PIECHART
    }
    private  int chartNumber;


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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.piechart_card_view, viewGroup,false
            );
            return new PieChartViewHolder(view);


    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        FetchDataToGraph fetchDataToGraph = new FetchDataToGraph(mContext);
        if(holder instanceof PieChartViewHolder)
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
        return 1;
    }

//    private int chartType;

    public void addChart()
    {
        chartNumber += 1;
        notifyItemInserted(getItemCount()-1);
//        llm.scrollToPosition(getItemCount()-1);
    }
}
