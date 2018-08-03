package com.tami.vmanager.adapter;

import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tami.vmanager.R;
import com.tami.vmanager.entity.MeetingChatPageResponse;
import com.tami.vmanager.manager.GlobaVariable;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Tang on 2018/7/9
 * 发送的消息
 */
public class MsgSendItemDelagate implements ItemViewDelegate<MeetingChatPageResponse.DataBean.ElementsBean> {
    private MeetingChatPageResponse.DataBean.ElementsBean item;

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_groupchat_me;
    }

    @Override
    public boolean isForViewType(MeetingChatPageResponse.DataBean.ElementsBean item, int position) {
        this.item = item;
        return item.getUserId() == GlobaVariable.getInstance().item.getId();
    }

    @Override
    public void convert(ViewHolder holder, MeetingChatPageResponse.DataBean.ElementsBean list, int position) {
        if (list != null) {
            ImageView igc_avatar_image = holder.getView(R.id.igc_avatar_image);
            if (!TextUtils.isEmpty(list.getUserIcon())) {
                Picasso.get().load(list.getUserIcon()).into(igc_avatar_image);
            } else {
                igc_avatar_image.setImageDrawable(ContextCompat.getDrawable(holder.getConvertView().getContext(),R.mipmap.touxiang));
            }
            holder.setText(R.id.igc_position_name, list.getUserName());
            holder.setText(R.id.igc_left_content, list.getContent());
        }
    }

    public MeetingChatPageResponse.DataBean.ElementsBean getItem(){
        return item;
    }
}
