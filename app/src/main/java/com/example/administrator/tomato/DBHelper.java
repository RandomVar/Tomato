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

    public static abstract class TaskItem implements BaseColumns {
        public static final String TABLE_NAME = "saved_tasks";
        public static final String COLUMN_NAME_TASK_NAME = "task_name";
        public static final String COLUMN_NAME_TASK_LENGTH="task_length";
    }

    public static abstract class PerformItem implements BaseColumns {
        public static final String TABLE_NAME = "performed_tasks";
        public static final String COLUMN_NAME_TASK_ID= "task_id";
        public static final String COLUMN_NAME_START_TIME="start_time";
        public static final String COLUMN_NAME_PREFORM_LENGTH="length";
        public static final String COLUMN_NAME_IS_FINISHED="is_finished";
    }

    private static final String CREATE_TASK =
            "CREATE TABLE " + TaskItem.TABLE_NAME + " (" +
                    TaskItem._ID + " INTEGER PRIMARY KEY" +","+
                    TaskItem. COLUMN_NAME_TASK_NAME + "TEXT" + ","+
                    TaskItem. COLUMN_NAME_TASK_LENGTH + "INTEGER" +")";

    private static final String CREATE_PERFORM =
            "CREATE TABLE " + PerformItem.TABLE_NAME + " (" +
                    PerformItem._ID + " INTEGER PRIMARY KEY" +","+
                    PerformItem.COLUMN_NAME_TASK_ID + "INTEGER" +","+
                    PerformItem.COLUMN_NAME_START_TIME+ "INTEGER" +","+
                    PerformItem.COLUMN_NAME_PREFORM_LENGTH + "INTEGER" +","+
                    PerformItem.COLUMN_NAME_IS_FINISHED + "INTEGER" +","+
                    "FOREIGN KEY("+ PerformItem.COLUMN_NAME_TASK_ID+")"+
                    "REFERENCES "+TaskItem.TABLE_NAME+"("+TaskItem._ID+")"+")";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TASK);
        db.execSQL(CREATE_PERFORM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    //需要：如果表2中没有任务记录则删
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


    public long addTask(String TaskName,long length) {

        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TaskItem.COLUMN_NAME_TASK_NAME, TaskName);
        cv.put(TaskItem.COLUMN_NAME_TASK_LENGTH, length);
        long rowId = db.insert(TaskItem.TABLE_NAME, null, cv);
        return rowId;
    }

    public void updateTask(int id, String newName,long newLength) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TaskItem.COLUMN_NAME_TASK_NAME, newName);
        cv.put(TaskItem.COLUMN_NAME_TASK_LENGTH, newLength);
        db.update(TaskItem.TABLE_NAME, cv,
                TaskItem._ID + "=" + id, null);
    }

    public long addPerform(int id,long startTime,long length)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PerformItem.COLUMN_NAME_TASK_ID,id);
        cv.put(PerformItem.COLUMN_NAME_START_TIME,startTime);
        cv.put(PerformItem.COLUMN_NAME_PREFORM_LENGTH,length);
        String[] projection = { TaskItem.COLUMN_NAME_TASK_LENGTH};
        Cursor c=db.query(TaskItem.TABLE_NAME,projection,TaskItem._ID+"=?", new String[]{String.valueOf(id)}, null, null, null);
        long finish=c.getInt(c.getColumnIndex(TaskItem.COLUMN_NAME_TASK_LENGTH));
        if(length>=finish)
            cv.put(PerformItem.COLUMN_NAME_IS_FINISHED,1);
        else cv.put(PerformItem.COLUMN_NAME_IS_FINISHED,0);
        c.close();
        long rowId=db.insert(PerformItem.TABLE_NAME, null, cv);
       return rowId;
    }


}
