package com.example.administrator.tomato;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CountDownActivity extends AppCompatActivity {
   private String TAG="CountDownActivity";
   private TextView mTvValue;
   private int id;
   private long startTime;
   private long TOTAL_TIME;
   private DBHelper mDataBase;
   private Boolean isFinish=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        mTvValue=(TextView)findViewById(R.id.countDown);
        mDataBase=new DBHelper(CountDownActivity.this);
        Intent intent = getIntent();
        id=intent.getIntExtra("task_id",0);
        Log.d("id!!",String.valueOf(id));
        TOTAL_TIME=(long)mDataBase.getTask(id).getLength();
        Log.d(TAG+'!',String.valueOf(TOTAL_TIME));
        TOTAL_TIME=TOTAL_TIME*60*1000;
        Log.d(TAG,String.valueOf(TOTAL_TIME));

         CountDownTimer countDownTimer = new CountDownTimer(TOTAL_TIME, 1000) {
             @Override
             public void onTick(long millisUntilFinished) {
                 long value =millisUntilFinished / 1000;
                 String mTv=String.valueOf(value/60)+":"+String.valueOf(value%60);
                 mTvValue.setText(mTv);
             }

             @Override
             public void onFinish() {
                 mTvValue.setText("00:00");
                 getFinish();
             }
         };
        startTime=SystemClock.elapsedRealtime();
        countDownTimer.start();

    }
    public void getFinish(){
        isFinish=true;
        RelativeLayout recording=(RelativeLayout)findViewById(R.id.recording);
        recording.setVisibility(View.GONE);
        RelativeLayout complete=(RelativeLayout)findViewById(R.id.complete);
        complete.setVisibility(View.VISIBLE);
        mDataBase.addPerform(id,startTime,TOTAL_TIME,isFinish);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            Log.e(TAG, "onBackPressed: 按下了返回键");
            if(!isFinish)
            {
                AlertDialog.Builder confirmDelete = new AlertDialog.Builder(CountDownActivity.this);
                confirmDelete.setTitle("取消任务");
                confirmDelete.setMessage("这项任务还未完成。您确认要取消这项任务吗？");
                confirmDelete.setCancelable(true);
                confirmDelete.setPositiveButton(getString(R.string.dialog_action_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {

                                    mDataBase.addPerform(id,startTime,SystemClock.elapsedRealtime()-startTime,isFinish);
                                    finish();
                                } catch (Exception e) {
                                    Log.e(TAG, "exception", e);
                                }

                                dialog.cancel();
                            }
                        });
                confirmDelete.setNegativeButton(getString(R.string.dialog_action_cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = confirmDelete.create();
                alert.show();
            }

        }
        return super.onKeyDown(keyCode, event);
    }




}
