package com.appmate.watchout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.appmate.watchout.R;
import com.appmate.watchout.activity.NewsFeedActivity;
import com.appmate.watchout.activity.SplashActivity;
import com.appmate.watchout.model.Data;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.MyViewHolder> {

    private Context mContext;
    private List<Data> dataList;
    private boolean isFilter;
    private String row_index = "";
    private ArrayList<MyViewHolder> holders = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_userName,tv_post_details;
        public Button btn_alert,btn_report;

        public MyViewHolder(View view) {
            super(view);
            tv_userName =  view.findViewById(R.id.tv_userName);
            tv_post_details =  view.findViewById(R.id.tv_post_details);
            btn_alert =  view.findViewById(R.id.btn_alert);
            btn_report =  view.findViewById(R.id.btn_report);
        }
    }

    public NewsFeedAdapter(Context context , List<Data> dataList) {
        this.mContext = context;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_feed, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(!holders.contains(holder) && holders.size() != dataList.size()){
            holders.add(holder);
        }
        final Data data = dataList.get(position);
        holder.tv_userName.setText(data.getUserName());
        holder.tv_post_details.setText(data.getEvent());
        holder.btn_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Implement Alert
                if (mContext instanceof NewsFeedActivity) {
                    ((NewsFeedActivity)mContext).updateData(position,"alert");
                }
            }
        });
        holder.btn_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Implement Report
                if (mContext instanceof NewsFeedActivity) {
                    ((NewsFeedActivity)mContext).updateData(position,"report");
                }
            }
        });
        if(data.getAlerterList() != null && data.getAlerterList().size() > 0){
            if(data.getAlerterList().contains(SplashActivity.mAuth.getCurrentUser().getUid())){
                holder.btn_alert.setEnabled(false);
            }
        }
        if(data.getReporterList() != null && data.getReporterList().size() > 0){
            if(data.getReporterList().contains(SplashActivity.mAuth.getCurrentUser().getUid())){
                holder.btn_report.setEnabled(false);
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
