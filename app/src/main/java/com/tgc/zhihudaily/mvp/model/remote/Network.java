package com.tgc.zhihudaily.mvp.model.remote;

import com.tgc.zhihudaily.mvp.model.bean.HandleEvent;
import com.tgc.zhihudaily.mvp.model.bean.NewsListBean;
import com.tgc.zhihudaily.mvp.model.bean.TopBean;
import com.tgc.zhihudaily.network.Api;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Network {

    private static Network instance;

    private Network(){

    }

    public static Network getInstance() {
        if (instance == null) {
            instance = new Network();
        }
        return instance;
    }

    public void getLeatestNews(String url) {
        Api.getInstance().service.getLeatestNews(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<TopBean>() {
                    @Override
                    public void accept(TopBean topBean) throws Exception {
                        EventBus.getDefault().post(new HandleEvent(HandleEvent.BANNER_GET_SUCCESS, topBean));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(new HandleEvent(HandleEvent.BANNER_GET_FAILED, throwable));
                    }
                });
    }

    public void getNewsList(String url) {
        Api.getInstance().service.getNewsList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<NewsListBean>() {
                    @Override
                    public void accept(NewsListBean newsListBean) throws Exception {
                        EventBus.getDefault().post(new HandleEvent(HandleEvent.NEWS_LIST_GET_SUCCESS, newsListBean));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(new HandleEvent(HandleEvent.NEWS_LIST_GET_FAILED, throwable));
                    }
                });
    }

    public void getOldNewsList(String url) {
        Api.getInstance().service.getNewsList(url)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<NewsListBean>() {
                    @Override
                    public void accept(NewsListBean newsListBean) throws Exception {
                        EventBus.getDefault().post(new HandleEvent(HandleEvent.NEWS_OLD_LIST_GET_SUCCESS, newsListBean));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        EventBus.getDefault().post(new HandleEvent(HandleEvent.NEWS_OLD_LIST_GET_FAILED, throwable));
                    }
                });
    }

}
