package com.tgc.zhihudaily.mvp.view;

import com.tgc.zhihudaily.base.BaseView;
import com.tgc.zhihudaily.mvp.model.bean.TopBean;

public interface HomeView extends BaseView {
    void setBanner(TopBean topBean);
}
