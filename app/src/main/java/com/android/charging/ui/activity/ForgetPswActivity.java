package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.util.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\20 0020 15:32
 * @class describe
 */
public class ForgetPswActivity extends BaseActivity {

    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.ed_user_phone)
    public EditText edUserPhone;
    @BindView(R.id.ed_phone_code)
    public EditText edPhoneCode;
    @BindView(R.id.btn_get_code)
    public Button btnGetCode;
    @BindView(R.id.ed_new_password)
    public EditText edNewPassword;
    @BindView(R.id.ed_affirm_password)
    public EditText edAffirmPassword;
    @BindView(R.id.btn_relogin)
    public Button btnRelogin;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, ForgetPswActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("忘记密码");
    }

    @OnClick({R.id.img_back, R.id.btn_get_code, R.id.btn_relogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.btn_get_code:
                break;
            case R.id.btn_relogin:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
