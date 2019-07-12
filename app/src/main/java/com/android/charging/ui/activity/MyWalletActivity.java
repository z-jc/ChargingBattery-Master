package com.android.charging.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.manager.ActManager;
import com.android.charging.manager.FragmentHelper;
import com.android.charging.ui.fragment.MyWalletExclusiveFragment;
import com.android.charging.ui.fragment.MyWalletUseFragment;
import com.android.charging.util.PermissionUtils;
import com.android.charging.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\24 0024 9:24
 * @class describe
 */
public class MyWalletActivity extends FragmentActivity {

    @BindView(R.id.img_back)
    public ImageView ivBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;
    @BindView(R.id.tv_use_wallet)
    public TextView tvUseWallet;            //通用
    @BindView(R.id.view_use_wallet)
    public View viewUseWallet;              //通用
    @BindView(R.id.tv_exclusive_wallet)
    public TextView tvExclusiveWallet;      //专属
    @BindView(R.id.view_exclusive_wallet)
    public View viewExclusiveWallet;        //专属

    private MyWalletUseFragment useFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, MyWalletActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setStatusBar();
        setContentView(R.layout.activity_my_wallet);
        ActManager.getAppManager().addActivity(this);
        ButterKnife.bind(this);
        initView();
    }

    protected void setStatusBar() {
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
    }

    private void initView() {
        int height = StatusBarUtil.getStatusBarHeight(this);
        rlTitle.setPadding(0, height, 0, 0);
        tvTitle.setText("我的钱包");

        showFragment();
    }

    private void showFragment() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        /**
         * 加载所有的fragment
         */
        useFragment = new MyWalletUseFragment();
        transaction.add(R.id.fragment_my_wallet, useFragment);
        //先隐藏所有的Fragment
        transaction.show(useFragment);//第一次启动只显示首页fragment
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        transaction.detach(useFragment);
    }

    @OnClick({R.id.img_back, R.id.tv_use_wallet, R.id.tv_exclusive_wallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.tv_use_wallet:
                showTitle1();
                break;
            case R.id.tv_exclusive_wallet:
                showTitle2();
                break;
        }
    }

    private void showTitle1() {
        FragmentHelper.switchFragment(new MyWalletUseFragment(), this, R.id.fragment_my_wallet);
        tvExclusiveWallet.setTextColor(0xFF000000);
        viewExclusiveWallet.setVisibility(View.GONE);
        tvUseWallet.setTextColor(0xFF0A94EA);
        viewUseWallet.setVisibility(View.VISIBLE);
    }

    private void showTitle2() {
        FragmentHelper.switchFragment(new MyWalletExclusiveFragment(), this, R.id.fragment_my_wallet);
        tvUseWallet.setTextColor(0xFF000000);
        viewUseWallet.setVisibility(View.GONE);
        tvExclusiveWallet.setTextColor(0xFF0A94EA);
        viewExclusiveWallet.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(getClass().getSimpleName(),"onDestroy...");
    }
}
