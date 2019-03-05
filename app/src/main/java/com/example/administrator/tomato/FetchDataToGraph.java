package com.example.administrator.tomato;

import android.content.Context;

import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;

public class FetchDataToGraph {
    private final String LOG_TAG= "FetchDataToGraph";
    private DBHelper mDatabase;
    private Context mContext;

    FetchDataToGraph(Context context)
    {
        mContext = context;
        mDatabase = new DBHelper(mContext);
    }

    public String test()
    {
        return Integer.toString(mDatabase.getTaskCount());
    }

    public LineDataSet getDataToLineChart()
    {

        return null;
    }

    public PieData getDataToPiechart()
    {
        return null;
    }

}
