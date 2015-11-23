package com.jay.android.fragmentforhost;

import android.app.Activity;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenSaverActivity extends Activity {
    protected static final String TAG = "ScreenSaverActivity";
    private static PowerManager.WakeLock mWakeLock;
private TextView tvDate;
    //	private PlayControl mPlayControl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_saver);
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP |
                PowerManager.SCREEN_DIM_WAKE_LOCK |
                PowerManager.ON_AFTER_RELEASE, "SimpleTimer");
        initViews();
    }

    @Override
    protected void onResume() {
        mWakeLock.acquire();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mWakeLock.release();
        super.onPause();
    }

    public void initViews() {
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        String strTime=format.format(date);
        tvDate = (TextView) findViewById(R.id.textDate);
        tvDate.setText(strTime);
        findViewById(R.id.btn_screen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
