package com.tgc.zhihudaily.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.tgc.zhihudaily.R;
import com.tgc.zhihudaily.adapter.LoadNewsAdapter;
import com.tgc.zhihudaily.base.App;
import com.tgc.zhihudaily.base.BaseFragment;
import com.tgc.zhihudaily.listener.EndlessRecyclerOnScrollListener;
import com.tgc.zhihudaily.mvp.model.bean.TopBean;
import com.tgc.zhihudaily.mvp.model.remote.BannerImageLoader;
import com.tgc.zhihudaily.mvp.presenter.HomePresenter;
import com.tgc.zhihudaily.mvp.view.HomeView;
import com.tgc.zhihudaily.utils.DialogUtils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.drawer_top)
    RelativeLayout drawerTop;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private HomePresenter presenter;
    private List<String> permissionsList = new ArrayList<>();
    private static final String[] permissions =
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private int days = 0;
    private LoadNewsAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {
        drawerTop.setOnClickListener(this);
    }

    @Override
    protected void setupView() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.index_page);
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_eeeeee));
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        checkPermissions();
    }

    @Override
    protected void initPresenter() {
        presenter = new HomePresenter();
        presenter.attachView(this);
    }

    public void checkPermissions() {
        permissionsList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(App.getContext(), permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsList.add(permissions[i]);
            }
        }
        if (permissionsList.isEmpty()) {
            load();
        } else {
            String[] permissionToCheck = permissionsList.toArray(new String[permissionsList.size()]);
            HomeFragment.this.requestPermissions(permissionToCheck, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length == 0) {
                    DialogUtils.alertFinishNoTitle(getContext(), getString(R.string.tips_666), getActivity());
                    return;
                }
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        getActivity().finish();
                        return;
                    }
                }
                load();
                break;
            default:
                break;
        }
    }

    // 需要动态权限申请之后的操作在此进行
    private void load() {
        presenter.getBanner();
        presenter.getNewsList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_top:
                showToast(R.string.please_login);
                break;
                default:
                    break;
        }
    }

    @Override
    public void setBanner(TopBean topBean) {
        List<String> imageList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (int i = 0; i < topBean.getTop_stories().size(); i++) {
            imageList.add(topBean.getTop_stories().get(i).getImage());
            titleList.add(topBean.getTop_stories().get(i).getTitle());
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new BannerImageLoader());
        banner.setImages(imageList);
        banner.setBannerAnimation(Transformer.Default);
        banner.setBannerTitles(titleList);
        banner.setDelayTime(2000);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
        Log.i("setBanner", "setBanner: ");
    }

    @Override
    public void setNewsList(List<String> titleList, List<String> imageList) {
        adapter = new LoadNewsAdapter();
        adapter.updateData(getContext(), titleList, imageList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                days++;
                presenter.getNewsList(days);
            }
        });
    }

    @Override
    public void setOldNewsList(List<String> titleList, List<String> imageList) {
        adapter.updateData(getContext(), titleList, imageList);
    }
}
