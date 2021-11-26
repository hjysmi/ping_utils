package com.lenovo.pingtools;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingTask extends AsyncTask<String, Integer, String> {
    Context context;

    public PingTask(Context context) {
        this.context = context;
    }

    protected String doInBackground(String... urls) {
        if(Integer.parseInt(urls[1])!=0){
            try {
                Thread.sleep(Integer.parseInt(urls[1]));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.e("xue", "doInBackground url:"+urls[0]);
        String s = PingUtil.ping("ping -c 1 " + urls[0]);
        return s;
    }

    protected void onProgressUpdate(Integer... progress) {
    }

    protected void onPostExecute(String result) {
        PingText ping_text = ping_config(result);
        context.sendBroadcast(new Intent("test")
                .putExtra("result", ping_text));

    }


    private static PingText ping_config(String result) {
        StringBuffer sb = new StringBuffer();
        PingText ping = new PingText();
        if ("".equals(result)) {
            sb.append("请求找不到主机, 请确认该URL是否有效");
            Log.e("xue", "请求找不到主机, 请确认该URL是否有效");
            ping.setError("请求找不到主机, 请确认该URL是否有效");
        } else {
            Log.e("xue", "onPostExecute " + result);

            Matcher matcher_host = (Pattern.compile("\\(([0-9]\\d*)\\.([0-9]\\d*)\\.([0-9]\\d*)\\.([0-9]\\d*)\\)")).matcher(result);
            Log.e("xue", "" + matcher_host.find());
            sb.append("来自");
            String ip = matcher_host.group();
            if (ip.contains("(") && ip.contains(")")) {
                ping.setIp(ip.substring(1, ip.length() - 1));
            }
            sb.append(matcher_host.group());
            String packet_loss_ = result.substring(result.indexOf("received,"));
            String packet_loss = packet_loss_.substring(9, packet_loss_.indexOf("packet"));
            ping.setPacket_loss(packet_loss.trim());
            Log.e("xue", "丢包率: " + packet_loss);

            Pattern p = Pattern.compile("(time=[1-9]\\d*\\.\\d* ms)|(time=[1-9]\\d* ms)");
            Matcher matcher = p.matcher(result);

            if (matcher.find()) {
                sb.append(matcher.group());
                Log.e("xue", "匹配上了 " + matcher.group());
                String timeout = matcher.group();
                ping.setTimeout(timeout.substring(5, timeout.length()-3));
            } else {
                Log.e("xue", "没有匹配上");
            }

            sb.append("丢包率:");
            sb.append(packet_loss);
            ping.setTime(getCurrentTime());
            Log.e("xue", "result:" + ping);
        }
        return ping;
    }

    private static String getCurrentTime(){
        long timecurrentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdfTwo =new SimpleDateFormat("MM-dd HH:mm:sss", Locale.getDefault());
        String time11 = sdfTwo.format(timecurrentTimeMillis);
        return time11;
    }
}
