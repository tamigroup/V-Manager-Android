package com.tami.vmanager.entity;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 获取会议流程单.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingItemFlowResponse extends MobileMessage implements Serializable {

    private static final long serialVersionUID = -3053896283452149187L;

    public GetMeetingItemFlowResponse() {
        super();
    }

    public Array data;

    @JsonInclude(Include.NON_NULL)
    public static class Array implements Serializable{

        private static final long serialVersionUID = 7376367472370338357L;
        public List<Item> dataList;

        @JsonInclude(Include.NON_NULL)
        public static class Item implements Serializable{
            private static final long serialVersionUID = -45118642126153762L;
            public int meetingItemId;	/*1*/
            public Long startOn;	/*1530547200000*/
            public int parentId;	/*0*/
            public int selectStatus;	/*0*/
            public int meetingItemSetId;	/*147*/
            public String meetingItemName;	/*会场就绪*/

            public List<UserList> meetingItemSetFlowUserList;
            @JsonInclude(Include.NON_NULL)
            public static class UserList implements Serializable{
                private static final long serialVersionUID = 2543297290413817025L;
                public String userName;	/*小塔米*/
                public String mobile;	/*13800138000*/
            }
        }
    }
}