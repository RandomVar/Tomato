package com.example.administrator.tomato;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class TaskFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = "TaskFragment";

    private int position;
    private TaskViewerAdapter mTaskViewerAdapter;

    private FloatingActionButton addButton=null;

    public TaskFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static TaskFragment newInstance(int position) {
        TaskFragment fragment = new TaskFragment();
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_task, container, false);

        addButton=(FloatingActionButton) v.findViewById(R.id.btnAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //加入一个任务
                //弹出新建任务的界面
//                Log.d(LOG_TAG,"!!!!!");
                addFileDialog();
            }
        });

        RecyclerView mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
//        LinearLayoutManager llm=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
//        mRecyclerView.setLayoutManager(llm);
//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        //newest to oldest order (database stores from oldest to newest)
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        //倒序展示，RecyclerView自动滑动到最后


        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mTaskViewerAdapter = new TaskViewerAdapter(getActivity(), llm);
        mRecyclerView.setAdapter(mTaskViewerAdapter);


        return v;
    }
    public void addFileDialog(){
        Log.d(LOG_TAG,"1!!!");
        AlertDialog.Builder addFileBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_updata_task, null);

        final EditText input1 = (EditText) view.findViewById(R.id.task_name);
        final EditText input2 = (EditText) view.findViewById(R.id.task_length);
        addFileBuilder.setTitle("新建任务");
        addFileBuilder.setCancelable(true);
        addFileBuilder.setPositiveButton(getContext().getString(R.string.dialog_action_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            String name = input1.getText().toString();
                            int length = Integer.parseInt(input2.getText().toString());
                            addTask(name,length);

                        } catch (Exception e) {
                            Log.e(LOG_TAG, "exception", e);
                        }

                        dialog.cancel();
                    }
                });
        addFileBuilder.setNegativeButton(getContext().getString(R.string.dialog_action_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        addFileBuilder.setView(view);
        AlertDialog alert = addFileBuilder.create();
        alert.show();
        Log.d(LOG_TAG,"2!!!");
    }
    public void addTask(String name,int length)
    {

        mTaskViewerAdapter.addTask(name,length);
    }

}
