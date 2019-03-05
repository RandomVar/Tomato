package com.example.administrator.tomato;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String LOG_TAG = "StatisticViewerAdapter";
    private Context mContext;
    private LinearLayoutManager llm;
//    private String [] titles;
    public enum ITEM_TYPE {
        LINERCHART,
        PIECHART
    }
    private  int chartNumber;

    //折线
    public static class LineChartViewHolder extends RecyclerView.ViewHolder {
        public LineChartViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    //扇形
    public static class PieChartViewHolder extends RecyclerView.ViewHolder {
        public PieChartViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

//    private int position;
    private LayoutInflater mLayoutInflater;

    public StatisticViewerAdapter(Context context,LinearLayoutManager linearLayoutManager)
    {
        super();
        mContext = context;
        llm = linearLayoutManager;
        chartNumber = 0;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int chartType) {

        if (chartType == ITEM_TYPE.LINERCHART.ordinal())
        {
            return new LineChartViewHolder(
                    mLayoutInflater.inflate(
                            R.layout.linechart_card_view, viewGroup,false
                    )
            );
        }
        else
        {
            return new LineChartViewHolder(
                    mLayoutInflater.inflate(
                            R.layout.piechart_card_view, viewGroup,false
                    )
            );
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
//        chartViewHolder.charType.setText("month");
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
