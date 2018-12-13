package com.tgc.zhihudaily.mvp.presenter;

import com.tgc.zhihudaily.base.BasePresenter;
import com.tgc.zhihudaily.base.BaseView;
import com.tgc.zhihudaily.mvp.model.bean.HandleEvent;
import com.tgc.zhihudaily.mvp.model.bean.NewsListBean;
import com.tgc.zhihudaily.mvp.model.bean.TopBean;
import com.tgc.zhihudaily.mvp.model.remote.Network;
import com.tgc.zhihudaily.mvp.view.HomeView;
import com.tgc.zhihudaily.utils.TimeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class HomePresenter extends BasePresenter {

    private HomeView view;
    private static final String LEATEST_NEWS_URL = "api/4/news/latest";
    private static final String NEWS_LIST_URL = "api/4/news/before/";

    public void getBanner() {
        Network.getInstance().getLeatestNews(LEATEST_NEWS_URL);
    }

    // 知乎API获取20181122的信息，URL后缀为20181123，获取20171130的信息，后缀为20171201
    public void getNewsList(int days) {
        try {
            String date = TimeUtils
                    .longToString(System.currentTimeMillis() - 86400 * 1000 * (days - 1), "yyyyMMdd");
            Network.getInstance().getOldNewsList(NEWS_LIST_URL + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void getNewsList() {
        try {
            String date = TimeUtils
                    .longToString(System.currentTimeMillis() + 86400 * 1000, "yyyyMMdd");
            Network.getInstance().getNewsList(NEWS_LIST_URL + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribe(HandleEvent event) {
        switch (event.getTag()) {
            case HandleEvent.BANNER_GET_SUCCESS:
                view.setBanner((TopBean) event.getObject());
                break;
            case HandleEvent.BANNER_GET_FAILED:
                break;
            case HandleEvent.NEWS_LIST_GET_SUCCESS:
                NewsListBean bean = (NewsListBean) event.getObject();
                List<String> titleList = new ArrayList<>();
                List<String> imageList = new ArrayList<>();
                for (int i = 0; i < bean.getStories().size(); i++) {
                    titleList.add(bean.getStories().get(i).getTitle());
                    imageList.add(bean.getStories().get(i).getImages().get(0));
                }
                view.setNewsList(titleList, imageList);
                break;
            case HandleEvent.NEWS_LIST_GET_FAILED:
                break;
            case HandleEvent.NEWS_OLD_LIST_GET_SUCCESS:
                NewsListBean oldBean = (NewsListBean) event.getObject();
                List<String> oldTitleList = new ArrayList<>();
                List<String> oldImageList = new ArrayList<>();
                for (int i = 0; i < oldBean.getStories().size(); i++) {
                    oldTitleList.add(oldBean.getStories().get(i).getTitle());
                    oldImageList.add(oldBean.getStories().get(i).getImages().get(0));
                }
                view.setOldNewsList(oldTitleList, oldImageList);
                break;
            case HandleEvent.NEWS_OLD_LIST_GET_FAILED:
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
