package com.tami.vmanager.entity;

import java.io.Serializable;

/**
 * Created by why on 2018/6/29.
 */
@Deprecated
public class MeetingPlaceSelectResponse extends MobileMessage implements Serializable {
    private static final long serialVersionUID = 8251209885853640056L;

    public MeetingPlaceSelectResponse() {
        super();
    }

    public Item data;

    public static class Item implements Serializable{
        private static final long serialVersionUID = -1056297069082600637L;

        private int id;
        private String name;
        private boolean state;

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

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }
    }
}
