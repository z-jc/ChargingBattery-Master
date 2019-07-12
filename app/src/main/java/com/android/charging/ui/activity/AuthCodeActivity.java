package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.util.ZhengZe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 10:49
 * @class describe
 */
public class AuthCodeActivity extends BaseActivity {

    @BindView(R.id.img_back)
    public ImageView ivBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;
    @BindView(R.id.tv_auth_code)
    public TextView tvAuthCode;
    @BindView(R.id.ed_phone_code)
    public EditText edPhoneCode;
    @BindView(R.id.btn_get_code)
    public Button btnGetCode;
    @BindView(R.id.btn_success)
    public Button btnSuccess;

    private static String TEL_PHONE;

    public static Intent newIntent(Activity activity, String phone) {
        Intent intent = new Intent(activity, AuthCodeActivity.class);
        intent.putExtra(TEL_PHONE, phone);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_code);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        TEL_PHONE = getIntent().getStringExtra(TEL_PHONE);
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("验证码");
        String str = "我们已发送短信到<font color='#029EE9'>" + ZhengZe.phoneToXX(TEL_PHONE) + "</font>";
        tvAuthCode.setText(Html.fromHtml(str));
    }

    @OnClick({R.id.btn_get_code, R.id.btn_success, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code:
                break;
            case R.id.btn_success:
                this.showLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AuthCodeActivity.this.dismissLoading();
                        startActivity(LoginActivity.newIntent(AuthCodeActivity.this));
                        AuthCodeActivity.this.finish();
                    }
                },3000);
                break;
            case R.id.img_back:
                this.finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
