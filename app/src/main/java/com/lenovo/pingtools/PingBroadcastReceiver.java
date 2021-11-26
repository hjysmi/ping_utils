package com.lenovo.pingtools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

class PingBroadcastReceiver extends BroadcastReceiver {

//    private Message message;

    @Override
    public void onReceive(Context context, Intent intent) {

        if("test".equals(intent.getAction())){
            PingText ping = (PingText) intent.getSerializableExtra("result");
//            message.getMsg(ping);
        }else if ((intent.getAction()).equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            Log.e("xue","飞行模式");
            int isAirplaneMode = Settings.System.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) ;
            Log.e("xue","飞行模式 " + isAirplaneMode);
            if(isAirplaneMode==0){

            }else{
                AlarmManagerUtils.getInstance(context).cancel();
            }
            Toast.makeText(context, "飞行模式 " + isAirplaneMode, Toast.LENGTH_SHORT).show();
        }
    }

//    interface Message{
//        public void getMsg(PingText msg);
//    }
//
//    public void setMsg(Message msg){
//        this.message = msg;
//    }

}
