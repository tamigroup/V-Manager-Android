package com.tami.vmanager.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 获取所有职位及职位下的人员列表.服务端响应
 *
 * @author 代码生成器v1.0
 */
@JsonInclude(Include.NON_NULL)
public class UserListOfPositionResponse extends MobileMessage implements Serializable {


    private static final long serialVersionUID = 4756775534506725977L;

    public UserListOfPositionResponse() {
        super();
    }

    public Item data;

    @JsonInclude(Include.NON_NULL)
    public static class Item implements Serializable{

        private static final long serialVersionUID = -3740736655803185647L;
        public List<UserListOfPositionResponse.Item.TitleItem> dataList;

        @JsonInclude(Include.NON_NULL)
        public static class TitleItem implements Serializable{

            private static final long serialVersionUID = -1982568783096227567L;
            public int id;	/*5*/
            public int orderBy;	/*0*/
            public int status;	/*0*/
            public int userCounts;	/*0*/
            public int systemId;	/*4*/
            public int depId;	/*31*/
            public String positionName;	/*rbqtest1*/
            public String departmentName;	/**/
            public Long createOn;	/*1528073029000*/
            public Long updateOn;	/*1530070917000*/
            public List<ContentList> userList;

            /**
             *
             */
            @JsonInclude(Include.NON_NULL)
            public static class ContentList implements Parcelable {

                public String registrationId;	/**/
                public int status;	/*0*/
                public String iconUrl;	/**/
                public String systemName;	/**/
                public String depName;	/**/
                public int depId;	/*5*/
                public String password;	/*63fb221131c62409a33301d16ee08c9f*/
                public int fromPlat;	/*1*/
                public int id;	/*14*/
                public String nickName;	/*测试用户1*/
                public String token;	/**/
                public int positionId;	/*5*/
                public int isAdmin;	/*1*/
                public String realName;	/*测试用户1*/
                public int systemId;	/*4*/
                public String userRoleList;	/**/
                public String positionName;	/**/
                public Long createOn;	/*1528092185000*/
                public Long updateOn;	/*1528092185000*/
                public int roleId;	/*0*/
                public String mobile;	/*13300000000*/

                public boolean isSelected;


                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.registrationId);
                    dest.writeValue(this.status);
                    dest.writeString(this.iconUrl);
                    dest.writeString(this.systemName);
                    dest.writeString(this.depName);
                    dest.writeValue(this.depId);
                    dest.writeString(this.password);
                    dest.writeValue(this.fromPlat);
                    dest.writeValue(this.id);
                    dest.writeString(this.nickName);
                    dest.writeString(this.token);
                    dest.writeValue(this.positionId);
                    dest.writeValue(this.isAdmin);
                    dest.writeString(this.realName);
                    dest.writeValue(this.systemId);
                    dest.writeString(this.userRoleList);
                    dest.writeString(this.positionName);
                    dest.writeValue(this.createOn);
                    dest.writeValue(this.updateOn);
                    dest.writeValue(this.roleId);
                    dest.writeString(this.mobile);
                    dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
                }

                public ContentList() {
                }

                protected ContentList(Parcel in) {
                    this.registrationId = in.readString();
                    this.status = (int) in.readValue(int.class.getClassLoader());
                    this.iconUrl = in.readString();
                    this.systemName = in.readString();
                    this.depName = in.readString();
                    this.depId = (int) in.readValue(int.class.getClassLoader());
                    this.password = in.readString();
                    this.fromPlat = (int) in.readValue(int.class.getClassLoader());
                    this.id = (int) in.readValue(int.class.getClassLoader());
                    this.nickName = in.readString();
                    this.token = in.readString();
                    this.positionId = (int) in.readValue(int.class.getClassLoader());
                    this.isAdmin = (int) in.readValue(int.class.getClassLoader());
                    this.realName = in.readString();
                    this.systemId = (int) in.readValue(int.class.getClassLoader());
                    this.userRoleList = in.readString();
                    this.positionName = in.readString();
                    this.createOn = (Long) in.readValue(Long.class.getClassLoader());
                    this.updateOn = (Long) in.readValue(Long.class.getClassLoader());
                    this.roleId = (int) in.readValue(int.class.getClassLoader());
                    this.mobile = in.readString();
                    this.isSelected = in.readByte() != 0;
                }

                public static final Creator<ContentList> CREATOR = new Creator<ContentList>() {
                    @Override
                    public ContentList createFromParcel(Parcel source) {
                        return new ContentList(source);
                    }

                    @Override
                    public ContentList[] newArray(int size) {
                        return new ContentList[size];
                    }
                };
            }
        }
    }

}