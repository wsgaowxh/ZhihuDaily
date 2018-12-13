package com.tgc.zhihudaily.mvp.model.bean;

public class HandleEvent {
    public static final int BANNER_GET_SUCCESS = 1;
    public static final int BANNER_GET_FAILED = 2;
    public static final int NEWS_LIST_GET_SUCCESS = 3;
    public static final int NEWS_LIST_GET_FAILED = 4;
    public static final int NEWS_OLD_LIST_GET_SUCCESS = 5;
    public static final int NEWS_OLD_LIST_GET_FAILED = 6;

    private int tag;
    private Object object;

    public HandleEvent(int tag, Object object) {
        this.tag = tag;
        this.object = object;
    }

    public HandleEvent(int tag) {
        this.tag = tag;
    }

    public HandleEvent() {
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
