package com.tgc.zhihudaily.base;

/**
 * Created by TGC on 2017/11/8.
 */

public abstract class BasePresenter {

    public abstract void attachView(BaseView view);

    public abstract void detachView();
}
