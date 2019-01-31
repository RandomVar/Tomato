package com.example.administrator.tomato;


import android.app.AlertDialog;
import android.content.Context;

import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class TaskViewerAdapter extends RecyclerView.Adapter<TaskViewerAdapter.TasksViewHolder> {

    private static final String LOG_TAG = "TaskViewerAdapter";
    Context mContext;
    LinearLayoutManager llm;
    private DBHelper mDatabase;
    private TaskInf item;
    public TaskViewerAdapter(Context context, LinearLayoutManager linearLayoutManager) {
        super();
        mContext = context;
        mDatabase = new DBHelper(mContext);
        llm = linearLayoutManager;
    }

    @Override
    public void onBindViewHolder(final TasksViewHolder holder, int position) {
        int itemId = mDatabase.getItemAt(position);
        Log.d(LOG_TAG,String.valueOf(position));
        Log.d(LOG_TAG,String.valueOf(itemId));
        item=mDatabase.getTask(itemId);
        Log.d(LOG_TAG,String.valueOf(item==null));
        Log.d(LOG_TAG,String.valueOf(item.getId()));
        Log.d(LOG_TAG,item.getName());
        Log.d(LOG_TAG,String.valueOf(item.getLength()));
        holder.vName.setText(item.getName());
        holder.vLength.setText(String.valueOf(item.getLength()));


        holder.cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
            //开始任务
               //开始倒计时的界面
           }
       });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
               //弹出修改/删除任务窗口
                ArrayList<String> entrys = new ArrayList<String>();
                entrys.add("Update Task");
                entrys.add("Delete Task");

                final CharSequence[] items = entrys.toArray(new CharSequence[entrys.size()]);


                // File delete confirm
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Options");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            updateFileDialog(holder.getPosition());
                        } else if (item == 1) {
                            deleteFileDialog(holder.getPosition());
                        }
                    }
                });
                builder.setCancelable(true);
                builder.setNegativeButton(mContext.getString(R.string.dialog_action_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

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
        protected TextView vLength;
        protected View cardView;
        public TasksViewHolder(View v) {
            super(v);
            vName = (TextView) v.findViewById(R.id.task_name);
            vLength = (TextView) v.findViewById(R.id.task_length);
            cardView = v.findViewById(R.id.card_view);
        }
    }

    @Override
    public int getItemCount() {
        Log.d("count",String.valueOf( mDatabase.getVisibleCount()));
        return mDatabase.getVisibleCount();
    }

    public void remove(int position) {
        //remove item from database, recyclerview and storage
        //delete file from storage
        int itemId=mDatabase.getItemAt(position);
        mDatabase.deleteFromVisible(itemId);
        Toast.makeText(mContext,"delete succesfully",Toast.LENGTH_SHORT).show();
        notifyItemRemoved(position);
    }
    public void updateTask(int position, String name,long length) {
            mDatabase.updateTask(mDatabase.getItemAt(position), name, length);
            Toast.makeText(mContext,"update sucessfully!",Toast.LENGTH_SHORT).show();

            notifyItemChanged(position);
        }
        public void addTask(String name,long length)
        {
            mDatabase.addTask(name,length);
            notifyItemInserted(getItemCount() - 1);
            llm.scrollToPosition(getItemCount() - 1);
        }


        public void  updateFileDialog(final int position)
        {
            AlertDialog.Builder renameFileBuilder = new AlertDialog.Builder(mContext);

            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.dialog_updata_task, null);

            final EditText input1 = (EditText) view.findViewById(R.id.task_name);
            final EditText input2 = (EditText) view.findViewById(R.id.task_length);
            renameFileBuilder.setTitle("重命名");
            renameFileBuilder.setCancelable(true);
            renameFileBuilder.setPositiveButton(mContext.getString(R.string.dialog_action_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            try {
                                String name = input1.getText().toString();
                                int length = Integer.parseInt(input2.getText().toString());
                                updateTask(position, name,length);

                            } catch (Exception e) {
                                Log.e(LOG_TAG, "exception", e);
                            }

                            dialog.cancel();
                        }
                    });
            renameFileBuilder.setNegativeButton(mContext.getString(R.string.dialog_action_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            renameFileBuilder.setView(view);
            AlertDialog alert = renameFileBuilder.create();
            alert.show();
        }
    public void deleteFileDialog (final int position) {
        // File delete confirm
        AlertDialog.Builder confirmDelete = new AlertDialog.Builder(mContext);
        confirmDelete.setTitle("删除任务");
        confirmDelete.setMessage("您确认要删除这项任务吗？");
        confirmDelete.setCancelable(true);
        confirmDelete.setPositiveButton(mContext.getString(R.string.dialog_action_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {

                            remove(position);

                        } catch (Exception e) {
                            Log.e(LOG_TAG, "exception", e);
                        }

                        dialog.cancel();
                    }
                });
        confirmDelete.setNegativeButton(mContext.getString(R.string.dialog_action_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = confirmDelete.create();
        alert.show();
    }
    }


