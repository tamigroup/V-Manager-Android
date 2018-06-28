package com.tami.vmanager.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.tami.vmanager.utils.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixishuang on 2018/4/19.
 */

public abstract class ListViewAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T> listData;
    private int layoutId;

    public ListViewAdapter(Context context, List<T> listData, @LayoutRes int layoutId) {
        this.context = context;
        if (listData == null) {
            this.listData = new ArrayList<>();
        } else {
            this.listData = listData;
        }
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public T getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 实例化一个ViewHolder
        ViewHolder holder = getViewHolder(position, convertView, parent);
        // 使用对外公开的convert方法，通过ViewHolder把View找到，通过Item设置值
        binView(holder, getItem(position), position);
        return holder.getmConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewHolder.getViewHolder(context, layoutId, convertView, parent);
    }

    protected abstract void binView(ViewHolder viewHolder, T item, int position);

    public void addData(T t) {
        this.listData.add(t);
        notifyDataSetChanged();
    }

    public void addData(int index, T t) {
        if (index < 0 || index > this.listData.size()) {
            Logger.d(this.getClass().getSimpleName() + "->追加数据失败！");
            return;
        }
        this.listData.add(index, t);
        notifyDataSetChanged();
    }

    public void addData(List<T> listData) {
        this.listData.addAll(listData);
        notifyDataSetChanged();
    }

    public void addData(int index, List<T> listData) {
        if (index < 0 || index > this.listData.size()) {
            Logger.d(this.getClass().getSimpleName() + "->追加数据失败！");
            return;
        }
        this.listData.addAll(index, listData);
        notifyDataSetChanged();
    }

    public void reloadData(List<T> listData) {
        if (this.listData.size() > 0) {
            this.listData.clear();
        }
        addData(listData);
    }

    public void removeData(int index) {
        if (index < 0 || index > this.listData.size()) {
            Logger.d(this.getClass().getSimpleName() + "->删除数据失败！");
            return;
        }
        this.listData.remove(index);
        notifyDataSetChanged();
    }

    public void removeData(T t) {
        if (t != null && this.listData.indexOf(t) == 0) {
            this.listData.remove(t);
            notifyDataSetChanged();
        } else {
            Logger.d(this.getClass().getSimpleName() + "->删除数据不存在！");
        }
    }
}
