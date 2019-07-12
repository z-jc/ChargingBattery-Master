package com.android.charging.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import com.android.charging.R;

/**
 * author cowards
 * created on 2019\3\26 0026
 **/
public class DialogLoading extends Dialog{

    private Activity activity;

    public DialogLoading(@NonNull Activity context) {
        super(context, R.style.dialog_commom);
        this.activity = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.dialog_loading);
        this.setCanceledOnTouchOutside(false);//点击dialog背景部分不消失
        this.setCancelable(false);//dialog出现时，点击back键不消失
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}