package com.example.administrator.tomato;


import android.content.Context;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class TaskViewerAdapter extends RecyclerView.Adapter<TaskViewerAdapter.TasksViewHolder> {

    private static final String LOG_TAG = "TaskViewerAdapter";
    Context mContext;
    LinearLayoutManager llm;

    public TaskViewerAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        super();
        mContext = context;
        llm = linearLayoutManager;
    }

    @Override
    public void onBindViewHolder(final TasksViewHolder holder, int position) {

        holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            //开始任务
           }
       });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               //弹出删除任务窗口

                return false;
            }
        });
    }

    @Override
    public TasksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_view, parent, false);

        mContext = parent.getContext();

        return new TasksViewHolder(itemView);
    }

    public static class TasksViewHolder extends RecyclerView.ViewHolder {
        protected TextView vName;
        protected View cardView;

        public TasksViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.task_name);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    @Override
    public int getItemCount() {
        //通过调用数据库中的getCount得到
        return 1;
    }

}
