package com.lenovo.pingtools;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static PingText ping_config(String result) {
        StringBuffer sb = new StringBuffer();
        PingText ping = new PingText();
        ping.setTime(getCurrentTime());
        if ("".equals(result)) {
            sb.append("请求找不到主机");
            Log.e("xue", "请求找不到主机");
            ping.setError("请求找不到主机");
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

            Log.e("xue", "result:" + ping);
        }
        return ping;
    }

    public static String getCurrentTime(){
        long timecurrentTimeMillis = System.currentTimeMillis();
        SimpleDateFormat sdfTwo =new SimpleDateFormat("MM-dd HH:mm:sss", Locale.getDefault());
        String time11 = sdfTwo.format(timecurrentTimeMillis);
        return time11;
    }
}
