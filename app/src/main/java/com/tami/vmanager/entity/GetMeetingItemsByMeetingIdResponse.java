package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * 根据会议ID查询会议节点信息.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingItemsByMeetingIdResponse extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 6076460614386615562L;

    public GetMeetingItemsByMeetingIdResponse() {
        super();
    }

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array implements Serializable{

        private static final long serialVersionUID = 7966621723684010822L;
        public List<Item> dataList;

        @JsonInclude(Include.NON_NULL)
        public static class Item implements Serializable {
            private static final long serialVersionUID = 5267019805566075419L;

            public Long startOn;	/*1527091200000*/
            public int meetingItemId;	/*3*/
            public int parentId;	/*0*/
            public int selectStatus;	/*0*/
            public int meetingItemSetId;	/*49*/
            public String meetingItemName;	/*茶歇就绪*/
//            public List<GetMeetingItemFlowResponse.Array.Item.UserList> meetingItemSetFlowUserList;
//
//            @JsonInclude(Include.NON_NULL)
//            public static class UserList {
//                public String userName;	/*小塔米*/
//                public String mobile;	/*13800138000*/
//            }
        }
    }
}