package com.tgc.zhihudaily;

import com.tgc.zhihudaily.base.BaseActivity;
import com.tgc.zhihudaily.fragment.HomeFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_content;
    }

    @Override
    protected void init() {
        replaceFragment(new HomeFragment(), R.id.content, false);
    }

    @Override
    protected void setupView() {

    }
}
