package com.lenovo.pingtools;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PingAdapter extends RecyclerView.Adapter<PingAdapter.ViewHolder> {

    private List<PingText> mPingList;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_ping;

        public ViewHolder (View view)
        {
            super(view);
            tv_ping = (TextView) view.findViewById(R.id.tv_ping);
        }

    }

    public  PingAdapter (List <PingText> pingList, Context context){
        mPingList = pingList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ping,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        PingText ping = mPingList.get(position);
        StringBuffer sb = new StringBuffer();
        if(null != ping.getError()){
            holder.tv_ping.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
            sb.append(ping.getTime());
            sb.append(" error:");
            sb.append(ping.getError());
        }else{
            if("100%".equals(ping.getPacket_loss())){
                holder.tv_ping.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
                sb.append(ping.getTime());
                sb.append(" ip:");
                sb.append(ping.getIp());
                sb.append(" 丢包率:");
                sb.append(ping.getPacket_loss());
            }else{
                sb.append(ping.getTime());
                sb.append(" ip:");
                sb.append(ping.getIp());
                sb.append(" time:");
                sb.append(ping.getTimeout());
                sb.append("ms");
                holder.tv_ping.setTextColor(context.getResources().getColor(R.color.mtrl_btn_text_color_disabled));
            }

        }
        holder.tv_ping.setText(sb.toString());

    }

    @Override
    public int getItemCount(){
        return mPingList.size();
    }
}
