package com.lenovo.pingtools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.TimerTask;

public class PingTimerTask extends TimerTask {
    Handler task_handler;
    String url;
    public void setHandler(Handler handler, String url){
        task_handler = handler;
        this.url = url;
    }

    @Override
    public void run() {
        Log.e("xue", "task");
        String result = PingUtil.ping("ping -c 1 " + url);
        PingText ping_text = Utils.ping_config(result);
        Message msg = new Message();
        msg.what = 100;
        Bundle bundle = new Bundle();
        bundle.putSerializable("msg", ping_text);
        msg.setData(bundle);
        task_handler.sendMessage(msg);
    }
}
