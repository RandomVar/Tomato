package com.example.administrator.tomato;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StatisticsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    //并不知道有什么用
    private static final String ARG_POSITION = "position";

    // TODO: Rename and change types of parameters
    private int position;
//    private String mParam1;
//    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(int position) {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
        }

    }

    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);



//        LineChart lineChart = (LineChart) view.findViewById(R.id.chart);
//        LineChart lineChart1 = (LineChart) view.findViewById(R.id.chart);
//
//        float[] dataObjects = {1, 2, 3, 4, 5, 6, 7, 6, 5, 4, 3, 2, 1};
//        List<Entry> entries = new ArrayList<>();
//        for (int i = 0; i < dataObjects.length; i++)
//        {
//            float data = dataObjects[i];
//            entries.add(new Entry(i, data));
//        }
//        LineDataSet dataSet = new LineDataSet(entries, "Label1");
//        dataSet.setColors(Color.BLACK, Color.GRAY, Color.RED, Color.GREEN);
//        // 每个点之间线的颜色，还有其他几个方法，自己看
//        dataSet.setValueFormatter(new IValueFormatter() {
//                                      // 将值转换为想要显示的形式，比如，某点值为1，变为“1￥”,MP提供了三个默认的转换器，
//                                      // LargeValueFormatter:将大数字变为带单位数字；
//                                      // PercentFormatter：将值转换为百分数；
//                                      // StackedValueFormatter，对于BarChart，是否只显示最大值图还是都显示
//                                      @Override
//                                      public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler)
//                                      {
//                                          return value + "￥";
//                                      }
//                                  });
//
//        LineData lineData = new LineData(dataSet);
//        lineChart.setData(lineData);
//        lineChart.setData(lineData);


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
