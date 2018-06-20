package com.tami.vmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tami.vmanager.R;
import com.tami.vmanager.activity.MeetingLinkConfirmedActivity;
import com.tami.vmanager.entity.TimeLine;

import java.util.List;

/**
 * Created by why on 2018/5/29.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineHolder> {

    private List<TimeLine> list;
    private Context context;

    private static final int TOP = 0;
    private static final int FOOT = 1;
    private static final int NORMAL = 2;

    @Override
    public TimeLineHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeLineHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_line, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TOP;
        } else if (position == list.size() - 1) {
            return FOOT;
        }
        return NORMAL;
    }

    public TimeLineAdapter(List<TimeLine> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(TimeLineHolder holder, final int position) {

        if (getItemViewType(position) == TOP) {
            holder.topView.setVisibility(View.INVISIBLE);
            holder.bottomView.setVisibility(View.VISIBLE);
            holder.lineView.setVisibility(View.VISIBLE);
        } else if (getItemViewType(position) == FOOT) {
            holder.topView.setVisibility(View.VISIBLE);
            holder.bottomView.setVisibility(View.INVISIBLE);
            holder.lineView.setVisibility(View.INVISIBLE);
        } else {
            holder.topView.setVisibility(View.VISIBLE);
            holder.bottomView.setVisibility(View.VISIBLE);
            holder.lineView.setVisibility(View.VISIBLE);
        }
        holder.contentView.setText(list.get(position).getConetnt());
        holder.stateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MeetingLinkConfirmedActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TimeLineHolder extends RecyclerView.ViewHolder {
        View topView;
        View bottomView;
        AppCompatImageView middlePic;
        TextView contentView;
        TextView stateBtn;
        View lineView;

        public TimeLineHolder(View itemView) {
            super(itemView);
            topView = itemView.findViewById(R.id.item_time_top_line);
            bottomView = itemView.findViewById(R.id.item_time_bottom_line);
            middlePic = itemView.findViewById(R.id.item_time_middle_pic);
            contentView = itemView.findViewById(R.id.item_time_content);
            stateBtn = itemView.findViewById(R.id.item_time_state);
            lineView = itemView.findViewById(R.id.item_time_line);
        }
    }
}
