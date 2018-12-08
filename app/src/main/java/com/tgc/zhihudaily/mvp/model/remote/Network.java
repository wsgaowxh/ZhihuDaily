package com.tgc.zhihudaily.mvp.model.remote;

import com.tgc.zhihudaily.mvp.model.bean.TopBean;
import com.tgc.zhihudaily.network.Api;

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

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

}
