package com.tgc.zhihudaily.mvp.presenter;

import com.tgc.zhihudaily.base.BasePresenter;
import com.tgc.zhihudaily.base.BaseView;
import com.tgc.zhihudaily.mvp.view.HomeView;

public class HomePresenter extends BasePresenter {

    private HomeView view;

    @Override
    public void attachView(BaseView view) {
        this.view = (HomeView) view;
    }

    @Override
    public void detachView() {

    }
}
