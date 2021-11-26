package com.lenovo.pingtools;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

class MyPhoneStateListener extends PhoneStateListener
{
//    Looper looper;


    public MyPhoneStateListener() {
        super();
    }

    public void onDataConnectionStateChanged(int state) {
        switch(state){
            case TelephonyManager.DATA_DISCONNECTED://网络断开
                Log.e("xue", "网络断开");
//                Toast.makeText(context, "网络断开", Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.DATA_CONNECTING://网络正在连接
                Log.e("xue", "网络正在连接");
//                Toast.makeText(context, "网络正在连接", Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.DATA_CONNECTED://网络连接上
                Log.e("xue", "网络连接上");
//                Toast.makeText(context, "网络连接上", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    @Override
    public void onDataActivity(int direction) {
        switch (direction){
            case TelephonyManager.DATA_ACTIVITY_NONE:
                Log.e("xue", "DATA_ACTIVITY_NONE");
                break;
            case TelephonyManager.DATA_ACTIVITY_IN:
                Log.e("xue", "DATA_ACTIVITY_IN");
                break;
            case TelephonyManager.DATA_ACTIVITY_OUT:
                Log.e("xue", "DATA_ACTIVITY_OUT");
                break;
            case TelephonyManager.DATA_ACTIVITY_INOUT:
                Log.e("xue", "DATA_ACTIVITY_INOUT");
                break;
            case TelephonyManager.DATA_ACTIVITY_DORMANT:
                Log.e("xue", "DATA_ACTIVITY_DORMANT");
                break;

        }
        super.onDataActivity(direction);
    }
}
