package com.tami.vmanager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lixishuang on 2018/4/19.
 */

public class ViewHolder {

    private SparseArray<View> itemView;
    private View convertView;

    // 构造函数
    private ViewHolder(Context context, int resLayoutId, ViewGroup parent) {
        this.itemView = new SparseArray<View>();
        this.convertView = LayoutInflater.from(context).inflate(resLayoutId, parent, false);
        this.convertView.setTag(this);
    }

    // 获取一个ViewHolder
    public static ViewHolder getViewHolder(Context context, int resLayoutId, View convertView, ViewGroup parent) {
        if (convertView == null) {
            return new ViewHolder(context, resLayoutId, parent);
        }
        return (ViewHolder) convertView.getTag();
    }

    public <T extends View> T getItemView(int id) {
        View view = itemView.get(id);
        if (view == null) {
            view = convertView.findViewById(id);
            itemView.put(id, view);
        }
        return (T) view;
    }

    // 获得一个convertView
    public View getmConvertView() {
        return convertView;
    }

    /**
     * 为TextView赋值
     */
    public void setTextView(int viewId, String text) {
        TextView view = getItemView(viewId);
        view.setText(text);
    }

    /**
     * 为ImageView赋值——drawableId
     */
    public void setImageResource(int viewId, int drawableId) {
        ImageView view = getItemView(viewId);
        view.setImageResource(drawableId);
    }

    /**
     * 为ImageView赋值——bitmap
     */
    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = getItemView(viewId);
        view.setImageBitmap(bitmap);
    }
}
