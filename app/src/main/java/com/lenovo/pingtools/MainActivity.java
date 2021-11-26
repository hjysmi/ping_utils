package com.lenovo.pingtools;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button run;
    Button clear;
    EditText et_ping;
    RecyclerView recy_view;

    List<PingText> ping = new ArrayList<>();
    PingAdapter adapter = new PingAdapter(ping, MainActivity.this);
//    PingBroadcastReceiver pingBR = new PingBroadcastReceiver(){
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if("test".equals(intent.getAction())){
//                PingText pingText = (PingText) intent.getSerializableExtra("result");
//                // 失败了就保存到sp中
//                if(null!=pingText){
//                    if("100%".equals(pingText.getPacket_loss())){
//                        SPUtils.get(MainActivity.this).putValue(UUID.randomUUID().toString(), pingText.toString());
//                    }
//                }
//
//                if(ping!=null){
//                    if(ping.size()>15){
//                        ping.subList(0, ping.size()-15).clear();
//                    }
//                    ping.add(pingText);
//                    adapter.notifyDataSetChanged();
//                }
//            }else if ((intent.getAction()).equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
//                Log.e("xue","飞行模式");
//                int isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
//                        Settings.Global.AIRPLANE_MODE_ON, 0) ;
//                Log.e("xue","飞行模式 " + isAirplaneMode);
//                if(isAirplaneMode==1){
//                    alarmManagerUtils.cancel();
//                }
//                Toast.makeText(context, "飞行模式 " + isAirplaneMode, Toast.LENGTH_SHORT).show();
//            }else if("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())){
//                ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//
//                if (networkInfo != null && networkInfo.isAvailable()) {
//                    //说明网络是连接的
//                    int type = networkInfo.getType();
//                    switch (type) {
//                        case ConnectivityManager.TYPE_MOBILE:  //移动网络
//                            String url = et_ping.getText().toString();
//                            if(!"".equals(url)&&run.getText().equals("停止")){
//                                alarmManagerUtils.createGetUpAlarmManager(et_ping.getText().toString());
//                                alarmManagerUtils.getUpAlarmManagerStartWork();
//                            }else{
//                                Log.e("xue","");
//                            }
//
//                            break;
//                    }
//                }
//            }
//        }
//    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer.purge();
        timer = null;
    }
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {

            if(msg.what==100){
                PingText pingText = (PingText) (msg.getData().getSerializable("msg"));
                // 失败了就保存到sp中
                if(null!=pingText){
                    if("100%".equals(pingText.getPacket_loss())){
                        StringBuffer sb = new StringBuffer();
                        sb.append("time:");
                        sb.append(pingText.getTime());
                        sb.append("  ");
                        sb.append("丢包率: 100%");
                        Log.e("xue", "handler 丢包: 100%");
                        SPUtils.get(MainActivity.this).putValue(UUID.randomUUID().toString(), sb.toString());
                    }
                }

                if(ping!=null){
                    if(ping.size()>25){
                        ping.subList(0, ping.size()-25).clear();
                    }
                    ping.add(pingText);
                    adapter.notifyDataSetChanged();
                }
            }


            return false;
        }
    });
    Timer timer;


    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clear = findViewById(R.id.clear);
        run = findViewById(R.id.run);
        et_ping = findViewById(R.id.et_ping);
        recy_view = findViewById(R.id.recy_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recy_view.setLayoutManager(layoutManager);
        recy_view.setAdapter(adapter);


        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(run.getText().equals("开始")){
                    run.setText("停止");
                    timer = new Timer();
                    PingTimerTask pingTimerTask = new PingTimerTask();
                    pingTimerTask.setHandler(handler, et_ping.getText().toString());
                    timer.schedule(pingTimerTask, 0, 300);
                    Log.e("xue", "click "+ et_ping.getText().toString());
                }else{
                    timer.cancel();
                    timer.purge();
                    timer = null;
                    run.setText("开始");
                }

            }
        });
    }



}