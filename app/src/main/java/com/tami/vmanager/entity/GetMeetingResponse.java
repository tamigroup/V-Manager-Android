package com.tami.vmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.io.Serializable;
import java.util.List;


/**
 * 查询会议概览信息.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class GetMeetingResponse extends MobileMessage implements Serializable {

    private static final long serialVersionUID = 364379347706851348L;
    public Item data;

    public GetMeetingResponse() {
        super();
    }

    @JsonInclude(Include.NON_NULL)
    public static class Item implements Serializable {

        public static final long serialVersionUID = 4172542378039500196L;

        public String saleUserName;	/*小塔米*/
        public int saleUserId;	/*会议创建人 销售Id*/
        public String meetingAddress;	/*满月楼*/
        public String eoUrl;	/*http://vgj.oss-cn-beijing.aliyuncs.com/app/af20180704180454_87882.png*/
        public int actualNum;	/*0*/
        public String isPay;	/*待付款*/
        public String perfectStatus;	/*待完善*/
        public int estimateNum;	/*300*/
        public int meetingAddressId;	/*2*/
        public int minNum;	/*280*/
        public Long endTime;	/*1531742400000*/
        public int fromPlat;	/*2*/
        public int isImportant;	/*4*/
        public Long startTime;	/*1531612800000*/
        public int followStatus;	/*0*/
        public String meetingName;	/*测试会议rbq20180705*/
        public String sponsorName;	/*rbqtest主办方*/
        public String meetingStatus;	/*待执行*/
        public int meetingId;	/*39*/
        public String autoDayTime;	/*7月15日08:00~7月16日20:00*/
        public int vzhStatus;	/*0*/
        public String salesUserMobile;	/*13800138000*/
        public int isVzh;
        public	String	cancelStatus;	/**/
        public List<VipGuest> vipGuestList;

        public List<VipReceiveUser> vipReceiveUser;

        @JsonInclude(Include.NON_NULL)
        public static class VipReceiveUser implements Serializable{

            private static final long serialVersionUID = -2573324300302154839L;

            public String registrationId;	/*1104a89792fa59a83a2*/
            public int status;	/*0*/
            public String systemName;	/**/
            public String iconUrl;	/*https://img2.woyaogexing.com/2018/05/16/84e2ae25d842cfc0!400x400_big.jpg*/
            public String depName;	/**/
            public String password;	/*3f57efeb8c91c6bae91ccd6e8790f8ad*/
            public int depId;	/*5*/
            public int fromPlat;	/*1*/
            public int id;	/*1*/
            public String token;	/**/
            public String nickName;	/*宁涛*/
            public int positionId;	/*6*/
            public int isAdmin;	/*1*/
            public String realName;	/*小塔米*/
            public int systemId;	/*4*/
            public String positionName;	/**/
            public Long createOn;	/*1526946603000*/
            public String mobile;	/*13800138000*/
            public int roleId;	/*2*/
            public Long updateOn;	/*1530517514000*/

            public List<UserRole> userRoleList;

            @JsonInclude(Include.NON_NULL)
            public static class UserRole implements Serializable{

                private static final long serialVersionUID = -8872250276884582827L;

                public int id;	/*1*/
                public int status;	/*0*/
                public int userId;	/*1*/
                public String roleName;	/**/
                public int systemId;	/*4*/
                public Long createOn;	/*1526948244000*/
                public int roleId;	/*3*/
                public Long updateOn;	/*1530155405000*/
            }
        }

        @JsonInclude(Include.NON_NULL)
        public static class VipGuest implements Serializable {

            private static final long serialVersionUID = -8741839249506663818L;

            public int id;	/*57*/
            public int status;	/*0*/
            public String name;	/*张子栋*/
            public int meetingId;	/*39*/
            public int systemId;	/*4*/
            public String intro;	/*爱笑会议室灵魂人特二*/
            public Long createOn;	/*1530836595000*/
            public Long updateOn;	/*1530836595000*/
        }
    }
}