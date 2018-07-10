package com.tami.vmanager.adapter;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.entity.MeetingChatPageResponse;
import com.tami.vmanager.manager.GlobaVariable;
import com.tami.vmanager.utils.SPUtils;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Tang on 2018/7/9
 * 收到的消息
 */
public class MsgComingItemDelagate implements ItemViewDelegate<MeetingChatPageResponse.DataBean.ElementsBean> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_group_chat;
    }

    @Override
    public boolean isForViewType(MeetingChatPageResponse.DataBean.ElementsBean item, int position) {
        return item.getUserId() != GlobaVariable.getInstance().item.getId();
    }

    @Override
    public void convert(ViewHolder holder, MeetingChatPageResponse.DataBean.ElementsBean list, int position) {
        ImageView igc_avatar_image = holder.getView(R.id.igc_avatar_image);
        Picasso.get().load(list.getUserIcon()).into(igc_avatar_image);
        SPUtils.put(holder.itemView.getContext(),"username",list.getUserName());
        holder.setText(R.id.igc_position_name, list.getUserName());
        holder.setText(R.id.igc_left_content, list.getContent());
    }
}
