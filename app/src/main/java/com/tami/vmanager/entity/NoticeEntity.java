package com.tami.vmanager.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.tami.vmanager.base.BaseEntity;


/**
 * Created by why on 2018/6/19.
 */
public class NoticeEntity extends BaseEntity implements Parcelable {

    private int id;
    private String name;
    private String content;
    private String time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.content);
        dest.writeString(this.time);
    }

    public NoticeEntity() {

    }

    protected NoticeEntity(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.content = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<NoticeEntity> CREATOR = new Parcelable.Creator<NoticeEntity>() {
        @Override
        public NoticeEntity createFromParcel(Parcel source) {
            return new NoticeEntity(source);
        }

        @Override
        public NoticeEntity[] newArray(int size) {
            return new NoticeEntity[size];
        }
    };
}
