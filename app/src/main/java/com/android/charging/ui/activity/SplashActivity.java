package com.android.charging.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.charging.R;
import com.android.charging.util.PermissionUtils;
import com.android.charging.util.ToastUtil;

import java.util.Arrays;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\18 0018 10:18
 * @class describe
 */
public class SplashActivity extends BaseActivity {

    private boolean isStart = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    private void start() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.newIntent(SplashActivity.this));
                finish();
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        PermissionUtils.checkMorePermissions(this, permission, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                if(!isStart){
                    isStart = true;
                    start();
                }
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                ToastUtil.showShortToast(SplashActivity.this, "拒绝权限可能会影响正常使用");
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                ToastUtil.showShortToast(SplashActivity.this, "拒绝权限可能会影响正常使用");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (isPermissions(grantResults)) {
                if(!isStart){
                    isStart = true;
                    start();
                }
            }
        }
    }

    private boolean isPermissions(int[] grantResults) {
        boolean isPermiss = true;
        for (int index : grantResults) {
            if (index == -1) {
                isPermiss = false;
            }
        }
        return isPermiss;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}