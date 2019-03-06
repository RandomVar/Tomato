package com.example.administrator.tomato;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

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
        List<PieEntry> mPie = new ArrayList<PieEntry>();
        PieData pieData = null;
        String [] taskName = new String[100];
        int [] time = new int[100];
        float [] part = new float[100];
        PerformInf temp;
        List list = mDatabase.getPerform();
        int size = 0;
        int sum = 0;
        int typeNum = 0;
        boolean had = false;
        int postionOfExsist = 0;

        for(int i = 0; i<list.size(); i++)
        {
            PerformInf item = (PerformInf) list.get(i);
            String listName = item.getName();
            System.out.println(listName);
            for (int j = 0; j < typeNum; j++)
            {
                System.out.println("处在循环"+Integer.toString(j)+"中"+"，此时taskName[j]是"+taskName[j]+"，listName是"+listName);
                System.out.println(taskName[j].equals(listName));
                if (taskName[j].equals(listName)) {
                    System.out.println(listName + "：此任务已存在");
                    had = true;
                    postionOfExsist = j;
                    break;
                }
            }
            if (!had) {
                taskName[typeNum] = item.getName();
                time[typeNum] = item.getLength();
                typeNum++;
            }
            else
                time[postionOfExsist] += item.getLength();
            sum += item.getLength();
            had = false;
        }
        for(int i = 0; i<typeNum; i++)
        {
            part[i] = ((float)time[i])/sum;
            part[i]=(int)(part[i]*100);
            //pieData.addDataSet(part[i]);

            PieEntry tempPieEntry = new PieEntry(part[i],taskName[i]);
            mPie.add(tempPieEntry);
        }
        PieDataSet pieDataSet = new PieDataSet(mPie,"图示");

//        设置颜色

        ArrayList<Integer> colors = new ArrayList<Integer>();
        int[] MATERIAL_COLORS = {
                Color.rgb(200, 172, 255)
        };
        for (int c : MATERIAL_COLORS) {
            colors.add(c);
        }
        for (int c : ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(c);
        }
        pieDataSet.setColors(colors);


//        最后一步装载
        pieData = new PieData(pieDataSet);

        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.DKGRAY);


        return pieData;
    }

//    public static void main(String[] args) {
//        int a = 5;
//        int b = 3;
//        float end = ((float) b)/a;
//        System.out.println(end);
//    }

}
