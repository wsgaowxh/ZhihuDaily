package com.tgc.zhihudaily.mvp.view;

import com.tgc.zhihudaily.base.BaseView;
import com.tgc.zhihudaily.mvp.model.bean.TopBean;

import java.util.List;

public interface HomeView extends BaseView {
    void setBanner(TopBean topBean);

    void setNewsList(List<String> titleList, List<String> imageList);

    void setOldNewsList(List<String> titleList, List<String> imageList);
}
