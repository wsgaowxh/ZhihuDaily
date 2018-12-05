package com.tgc.zhihudaily.fragment;


import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.tgc.zhihudaily.R;
import com.tgc.zhihudaily.base.App;
import com.tgc.zhihudaily.base.BaseFragment;
import com.tgc.zhihudaily.mvp.presenter.HomePresenter;
import com.tgc.zhihudaily.mvp.view.HomeView;
import com.tgc.zhihudaily.utils.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeView {

    private HomePresenter presenter;
    private List<String> permissionsList = new ArrayList<>();
    private static final String[] permissions =
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void setupView() {

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

    }
}
