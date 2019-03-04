package com.example.administrator.tomato;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private Context mContext;

    private static final String LOG_TAG = "DBHelper";

    public static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;

    //应该是保存任务的表
    public static abstract class TaskItem implements BaseColumns {
        public static final String TABLE_NAME = "saved_tasks";
        public static final String COLUMN_NAME_TASK_NAME = "task_name";
        public static final String COLUMN_NAME_TASK_LENGTH="task_length";
    }

    //历史记录？
    public static abstract class PerformItem implements BaseColumns {
        public static final String TABLE_NAME = "performed_tasks";
        public static final String COLUMN_NAME_TASK_ID= "task_id";
        public static final String COLUMN_NAME_START_TIME="start_time";
        public static final String COLUMN_NAME_PREFORM_LENGTH="length";
        public static final String COLUMN_NAME_IS_FINISHED="is_finished";
    }

    //可见的任务
    public static abstract class VisibleItem implements BaseColumns {
        public static final String TABLE_NAME = "visible_tasks";
        public static final String COLUMN_NAME_TASK_ID = "task_id";

    }
    //创建任务表
    private static final String CREATE_TASK =
            "CREATE TABLE " + TaskItem.TABLE_NAME + " (" +
                    TaskItem._ID + " INTEGER PRIMARY KEY" +","+
                    //任务id，自动增长
                    TaskItem. COLUMN_NAME_TASK_NAME + " TEXT" + ","+
                    //任务名
                    TaskItem. COLUMN_NAME_TASK_LENGTH + " INTEGER" +")";
                    //任务时长
    //创建任务执行情况表
    private static final String CREATE_PERFORM =
            "CREATE TABLE " + PerformItem.TABLE_NAME + " (" +
                    PerformItem._ID + " INTEGER PRIMARY KEY" +","+
                    PerformItem.COLUMN_NAME_TASK_ID + " INTEGER" +","+
                    PerformItem.COLUMN_NAME_START_TIME+ " INTEGER" +","+
                    PerformItem.COLUMN_NAME_PREFORM_LENGTH + " INTEGER" +","+
                    PerformItem.COLUMN_NAME_IS_FINISHED + " INTEGER" +","+
                    "FOREIGN KEY("+ PerformItem.COLUMN_NAME_TASK_ID+") "+
                    "REFERENCES "+TaskItem.TABLE_NAME+"("+TaskItem._ID+")"+")";
    //创建记录可见任务的表
    private static final String CREATE_VISIBLE =
            "CREATE TABLE " + VisibleItem.TABLE_NAME + " (" +
                    VisibleItem._ID + " INTEGER PRIMARY KEY" +","+
                    VisibleItem. COLUMN_NAME_TASK_ID + " INTEGER" + ","+
                    "FOREIGN KEY("+ VisibleItem.COLUMN_NAME_TASK_ID+") "+
                    "REFERENCES "+TaskItem.TABLE_NAME+"("+TaskItem._ID+")"+ ")";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TASK);
        db.execSQL(CREATE_PERFORM);
        db.execSQL(CREATE_VISIBLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    //如果表2中没有任务记录则删
    public void removeTaskWithId(int id) {
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = { String.valueOf(id) };
        try{
            db.delete(TaskItem.TABLE_NAME, "_ID=?", whereArgs);
        }catch (Exception e)
        {
            Log.d(LOG_TAG,"Cannot delete");
        }
    }

    public Context getContext() {
        return mContext;
    }

    //共有任务数量
    public int getTaskCount() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { TaskItem._ID };
        Cursor c = db.query(TaskItem.TABLE_NAME, projection, null, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;
    }


  //加入一项任务
    public long addTask(String TaskName,long length) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TaskItem.COLUMN_NAME_TASK_NAME, TaskName);
        cv.put(TaskItem.COLUMN_NAME_TASK_LENGTH, length);
        long rowId = db.insert(TaskItem.TABLE_NAME, null, cv);
        Cursor c=db.query(TaskItem.TABLE_NAME,new String[]{TaskItem._ID},null,null,null,null,null);
        c.moveToPosition((int) (rowId-1));
        int id=c.getInt(c.getColumnIndex(TaskItem._ID));

        Log.d("rowid1",String.valueOf(rowId));
        Log.d("id",String.valueOf(id));
        cv.clear();
        cv.put(VisibleItem.COLUMN_NAME_TASK_ID,id);
        rowId=db.insert(VisibleItem.TABLE_NAME, null, cv);
        Log.d("rowid2",String.valueOf(rowId));
        return rowId;
    }
    //更新任务信息
    public void updateTask(int id, String newName,long newLength) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TaskItem.COLUMN_NAME_TASK_NAME, newName);
        cv.put(TaskItem.COLUMN_NAME_TASK_LENGTH, newLength);
        db.update(TaskItem.TABLE_NAME, cv,
                TaskItem._ID + "=" + id, null);
    }

    //得到对应id的任务信息
    public TaskInf getTask(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {TaskItem._ID,TaskItem.COLUMN_NAME_TASK_NAME,TaskItem.COLUMN_NAME_TASK_LENGTH};
        Cursor c = db.query(TaskItem.TABLE_NAME, projection, TaskItem._ID+"=?", new String[]{String.valueOf(id)}, null, null, null);

        if(c.moveToFirst()!=false) {
           TaskInf item = new TaskInf();
           item.setId(c.getInt(c.getColumnIndex(TaskItem._ID)));
           item.setName(c.getString(c.getColumnIndex(TaskItem.COLUMN_NAME_TASK_NAME)));
           item.setLength(c.getInt(c.getColumnIndex(TaskItem.COLUMN_NAME_TASK_LENGTH)));
           c.close();
           return item;
       }
       Log.d(LOG_TAG,"error");
       return null;
      }


    //完成一次任务
    public long addPerform(int id,long startTime,long length,boolean isFinished)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PerformItem.COLUMN_NAME_TASK_ID,id);
        cv.put(PerformItem.COLUMN_NAME_START_TIME,startTime);
        cv.put(PerformItem.COLUMN_NAME_PREFORM_LENGTH,length);
        cv.put(PerformItem.COLUMN_NAME_IS_FINISHED,isFinished);
//        String[] projection = { TaskItem.COLUMN_NAME_TASK_LENGTH};
//        Cursor c=db.query(TaskItem.TABLE_NAME,projection,TaskItem._ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
//        long finish=c.getInt(c.getColumnIndex(TaskItem.COLUMN_NAME_TASK_LENGTH));
//        if(length>=finish)
//            cv.put(PerformItem.COLUMN_NAME_IS_FINISHED,1);
//        else cv.put(PerformItem.COLUMN_NAME_IS_FINISHED,0);
//        c.close();
        long rowId=db.insert(PerformItem.TABLE_NAME, null, cv);
       return rowId;
    }

    //通过Visible表得到视图中position个任务在task表中的编号
    public int getItemAt(int position) {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {VisibleItem.COLUMN_NAME_TASK_ID};
        Cursor c = db.query(VisibleItem.TABLE_NAME, projection, null, null, null, null, null);
        if (c.moveToPosition(position)) {
            int value= c.getInt(c.getColumnIndex(VisibleItem.COLUMN_NAME_TASK_ID));

            c.close();
            return value;
        }
        return -1;
    }

    //可见的数据数量
    public int getVisibleCount() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = { VisibleItem._ID };
        Cursor c = db.query(VisibleItem.TABLE_NAME, projection, null, null, null, null, null);
        int count = c.getCount();
        c.close();
        return count;
    }
    //在可见表中删除一项任务
    public void deleteFromVisible(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        String[] whereArgs = { String.valueOf(id) };
        db.delete(VisibleItem.TABLE_NAME, "_ID=?", whereArgs);
    }


}
