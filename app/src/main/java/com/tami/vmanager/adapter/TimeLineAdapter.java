package com.tami.vmanager.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tami.vmanager.R;
import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdResponse;
import com.tami.vmanager.utils.TimeUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by why on 2018/5/29.
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineHolder> {

    private List<GetMeetingItemsByMeetingIdResponse.Array.Item> list;
    private Context context;

    private static final int TOP = 0;
    private static final int FOOT = 1;
    private static final int NORMAL = 2;

    private TimeLineMeetingFlowItem tlmfi;

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

    public TimeLineAdapter(List<GetMeetingItemsByMeetingIdResponse.Array.Item> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(TimeLineHolder holder, final int position) {
        GetMeetingItemsByMeetingIdResponse.Array.Item item = list.get(position);
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
        holder.contentView.setText(TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_HHMM_SLASH) + "  " + item.meetingItemName + "  " + TimeUtils.date2String(new Date(item.startOn), TimeUtils.DATE_MMDD_SLASH));

        if (position != 0 && list.get(position - 1).selectStatus == 1) {
            holder.topView.setBackgroundResource(R.color.color_34DB8E);
        } else {
            holder.topView.setBackgroundResource(R.color.color_EAEAEA);
        }
        if (item.selectStatus == 1) {
            holder.stateBtn.setText(context.getString(R.string.confirmed));
            holder.middlePic.setImageResource(R.mipmap.middle_pic_selected);
            holder.bottomView.setBackgroundResource(R.color.color_34DB8E);
        } else if (item.selectStatus == 2) {
            holder.stateBtn.setText(context.getString(R.string.ready));
            holder.middlePic.setImageResource(R.mipmap.create_meeting_delete);
            holder.bottomView.setBackgroundResource(R.color.color_EAEAEA);
        } else if (item.selectStatus == 3) {
            holder.stateBtn.setText(context.getString(R.string.ready));
            holder.middlePic.setImageResource(R.mipmap.middle_pic_unselected);
            holder.bottomView.setBackgroundResource(R.color.color_EAEAEA);
        }
        holder.stateBtn.setOnClickListener((View view) -> {
            if (tlmfi != null) {
                tlmfi.getMeetingFlowItem(item);
            }
        });
    }

    public void setTimeLineMeetingFlowItem(TimeLineMeetingFlowItem timeLineMeetingFlowItem) {
        this.tlmfi = timeLineMeetingFlowItem;
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
