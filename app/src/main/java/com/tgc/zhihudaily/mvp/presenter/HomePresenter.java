package com.tgc.zhihudaily.mvp.presenter;

import com.tgc.zhihudaily.base.BasePresenter;
import com.tgc.zhihudaily.base.BaseView;
import com.tgc.zhihudaily.mvp.model.bean.HandleEvent;
import com.tgc.zhihudaily.mvp.model.bean.TopBean;
import com.tgc.zhihudaily.mvp.model.remote.Network;
import com.tgc.zhihudaily.mvp.view.HomeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class HomePresenter extends BasePresenter {

    private HomeView view;
    private static final String LEATEST_NEWS_URL = "api/4/news/latest";

    public void getBanner() {
        Network.getInstance().getLeatestNews(LEATEST_NEWS_URL);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(HandleEvent event) {
        switch (event.getTag()) {
            case HandleEvent.BANNER_GET_SUCCESS:
                view.setBanner((TopBean) event.getObject());
                break;
            case HandleEvent.BANNER_GET_FAILED:
                break;
                default:
                    break;
        }
    }

    @Override
    public void attachView(BaseView view) {
        this.view = (HomeView) view;
        EventBus.getDefault().register(this);
    }

    @Override
    public void detachView() {
        EventBus.getDefault().unregister(this);
    }
}
