package com.lenovo.pingtools;

import java.io.Serializable;

public class PingText implements Serializable {
    private String packet_loss;
    private String ip;
    private String time;
    private String timeout;
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public PingText() {
    }

    public String getPacket_loss() {
        return packet_loss;
    }

    public void setPacket_loss(String packet_loss) {
        this.packet_loss = packet_loss;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "PingText{" +
                "packet_loss='" + packet_loss + '\'' +
                ", ip='" + ip + '\'' +
                ", time='" + time + '\'' +
                ", timeout='" + timeout + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
