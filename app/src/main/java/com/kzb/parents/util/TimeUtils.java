package com.kzb.parents.util;

import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by wanghaofei on 16/10/31.
 */

public class TimeUtils {
    private int time = 60 * 10;

    private Timer timer;

    private TextView btnSure;


    public TimeUtils(TextView btnSure, int timeval) {
        super();
        this.btnSure = btnSure;
        time = timeval * 60;
    }


    public void RunTimer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                time--;
                Message msg = handler.obtainMessage();
                msg.what = 1;
                handler.sendMessage(msg);

            }
        };


        timer.schedule(task, 1000, 1000);
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:

                    if (time > 0) {
                        btnSure.setEnabled(false);

                        int min = time / 60;
                        int secs = time % 60;

                        if (secs >= 0 && secs < 10) {
                            btnSure.setText(min + ":" + "0" + secs);
                            btnSure.setTextSize(14);
                        } else {
                            btnSure.setText(min + ":" + secs);
                            btnSure.setTextSize(14);
                        }


                    } else {

                        timer.cancel();
                        btnSure.setText("时间结束");
                        btnSure.setEnabled(true);
                        btnSure.setTextSize(14);
                    }

                    break;


                default:
                    break;
            }

        }

        ;
    };


}