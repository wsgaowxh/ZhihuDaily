package com.tgc.zhihudaily.network;

import com.tgc.zhihudaily.mvp.model.bean.TopBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET
    Observable<TopBean> getLeatestNews(@Url String url);

}
