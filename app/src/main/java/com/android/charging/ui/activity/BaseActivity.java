package com.android.charging.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.android.charging.manager.ActManager;
import com.android.charging.ui.dialog.DialogLoading;
import com.android.charging.util.EdittorUtil;
import com.android.charging.util.PermissionUtils;
import com.android.charging.util.StatusBarUtil;
import com.android.charging.util.ToastUtil;

import java.util.Arrays;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\18 0018 10:18
 * @class describe
 */
public class BaseActivity extends Activity {

    public String TAG = getClass().getSimpleName();

    public String[] permission = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,};
    public int height;
    private int REQUESTCODE = 0x01;

    public DialogLoading dialogLoading = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActManager.getAppManager().addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//强制竖屏
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
        setStatusBar();
        if (PermissionUtils.isVersionCodeM()) {
            PermissionUtils.requestMorePermissions(this, permission, REQUESTCODE);
        } else {
            Log.e(TAG, "Android版本低于6.0,不用申请权限");
        }
    }

    protected void setStatusBar() {
        height = StatusBarUtil.getStatusBarHeight(this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTCODE) {
            Log.e(TAG, "permissions:" + Arrays.toString(permissions));
            Log.e(TAG, "grantResults:" + Arrays.toString(grantResults));
            //权限被拒绝
            for (int grantResult : grantResults) {
                if (grantResult == -1) {
                    Intent intent = getAppDetailSettingIntent(BaseActivity.this);
                    startActivity(intent);
                    continue;
                }
            }
        }
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    public void showLoading(){
        if(dialogLoading != null){
            dialogLoading.dismiss();
            dialogLoading = null;
        }
        dialogLoading = new DialogLoading(this);
        dialogLoading.show();
    }

    public void dismissLoading(){
        if(dialogLoading != null){
            dialogLoading.dismiss();
            dialogLoading = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissLoading();
        EdittorUtil.hideInput(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActManager.getAppManager().finishActivity(this);
    }
}