package com.tami.vmanager.adapter;

import com.tami.vmanager.entity.GetMeetingItemsByMeetingIdResponse;

/**
 * Created by why on 2018/7/14.
 */

public interface TimeLineMeetingFlowItem {

    public void getMeetingFlowItem(GetMeetingItemsByMeetingIdResponse.Array.Item item);
}
