package com.tami.vmanager.adapter;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.tami.vmanager.R;
import com.tami.vmanager.entity.GetMeetingItemFlowResponse;
import com.tami.vmanager.entity.TimeLine;
import java.util.List;

/**
 * Created by why on 2018/6/19.
 */
public class TimeLineHorizontalAdapter extends RecyclerView.Adapter<TimeLineHorizontalAdapter.TimeLineHorizontaHolder> {

    private List<GetMeetingItemFlowResponse.Array.Item> list;
    private static final int TOP = 0;
    private static final int FOOT = 1;
    private static final int NORMAL = 2;

    @Override
    public TimeLineHorizontaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimeLineHorizontaHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_horizontal_line, parent, false));
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

    public TimeLineHorizontalAdapter(List<GetMeetingItemFlowResponse.Array.Item> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(TimeLineHorizontaHolder holder, int position) {
        if (getItemViewType(position) == TOP) {
            holder.leftView.setVisibility(View.INVISIBLE);
            holder.rightView.setVisibility(View.VISIBLE);
        } else if (getItemViewType(position) == FOOT) {
            holder.leftView.setVisibility(View.VISIBLE);
            holder.rightView.setVisibility(View.INVISIBLE);
        } else {
            holder.leftView.setVisibility(View.VISIBLE);
            holder.rightView.setVisibility(View.VISIBLE);
        }
        holder.stateTxt.setText(list.get(position).meetingItemName);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TimeLineHorizontaHolder extends RecyclerView.ViewHolder {
        View leftView;
        View rightView;
        AppCompatImageView middlePic;
        TextView stateTxt;

        public TimeLineHorizontaHolder(View itemView) {
            super(itemView);
            leftView = itemView.findViewById(R.id.thl_left_line);
            rightView = itemView.findViewById(R.id.thl_right_line);
            middlePic = itemView.findViewById(R.id.thl_middle_pic);
            stateTxt = itemView.findViewById(R.id.thl_state);
        }
    }
}
