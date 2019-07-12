package com.android.charging.manager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.charging.R;

/**
 * @author Administrator
 * @particulars
 * @time 2019\5\14 0014 18:57
 * @class describe
 */
public class FragmentHelper {

    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

    private static FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    private static void setFragmentManager(FragmentManager fragmentManager) {
        FragmentHelper.fragmentManager = fragmentManager;
    }

    public static FragmentTransaction getFragmentTransaction() {
        return fragmentTransaction;
    }

    public static void setFragmentTransaction(FragmentTransaction fragmentTransaction) {
        FragmentHelper.fragmentTransaction = fragmentTransaction;
    }

    private static void initFragmentTransaction() {
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    /**
     * 此方法在onBackPressed被重写时使用
     * 回退到上一层fragment
     * 如果已经是最后一层，隐藏界面
     *
     * @param activity 当前activity，仅支持AppCompatActivity
     *                 在fragment中请使用(AppCompatActivity)getActivity()作为参数传入
     */
    public static void back(FragmentActivity activity) {
        if (getFragmentManager().getBackStackEntryCount() <= 1) {
            activity.moveTaskToBack(true);
        } else {
            fragmentManager.popBackStack();
        }
    }

    /**
     * 切换Fragment为传入参数
     *
     * @param activity 当前activity，仅支持AppCompatActivity
     *                 在fragment中请使用(AppCompatActivity)getActivity()作为参数传入
     * @param fragment 目标fragment对象
     */
    public static void switchFragment(Fragment fragment, FragmentActivity activity, int id) {
        FragmentHelper.setFragmentManager(activity.getSupportFragmentManager());
        FragmentHelper.initFragmentTransaction();
        //frame容器id
        fragmentManager.findFragmentById(id);
        fragmentTransaction
                .replace(id, fragment)
                .addToBackStack(null)
                .commit();
    }
}
