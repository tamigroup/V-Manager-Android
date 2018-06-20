package com.tami.vmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.tami.vmanager.R;

/**
 * Created by why on 2018/6/15.
 */

public class MeetingFlowEditAdapter extends BaseExpandableListAdapter {

    private String[] arrayTile = new String[]{"上午", "下午", "晚上", "期它"};
    private String[][] arrayContent = new String[][]{
            {"会场就绪", "荼歇就绪", "会场午餐就绪", "餐厅午餐就绪"},
            {"会场就绪", "荼歇就绪", "会场晚餐就绪", "餐厅晚餐就绪"},
            {"会场就绪", "荼歇就绪", "会场晚宴就绪", "餐厅晚宴就绪"},
            {"加强保安", "鲜花陈列", "前台接待"}};
    private Context context;

    public MeetingFlowEditAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return arrayTile.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arrayContent[groupPosition].length;
    }

    @Override
    public String getGroup(int groupPosition) {
        return arrayTile[groupPosition];
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return arrayContent[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    //        获取显示指定分组的视图
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_meeting_flow_edit_group, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.tvTitle = convertView.findViewById(R.id.mfe_group_name);
            groupViewHolder.line = convertView.findViewById(R.id.mfe_seiz_seat);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        if (groupPosition == 0) {
            groupViewHolder.line.setVisibility(View.GONE);
        } else {
            groupViewHolder.line.setVisibility(View.VISIBLE);
        }
        groupViewHolder.tvTitle.setText(arrayTile[groupPosition]);
        return convertView;
    }

    //        获取显示指定分组中的指定子选项的视图
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_meeting_flow_edit_child, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.tvTitle = convertView.findViewById(R.id.mfe_child_name);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        childViewHolder.tvTitle.setText(arrayContent[groupPosition][childPosition]);
        return convertView;
    }

    //        指定位置上的子元素是否可选中
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class GroupViewHolder {
        TextView tvTitle;
        View line;
    }

    static class ChildViewHolder {
        TextView tvTitle;
    }
}
