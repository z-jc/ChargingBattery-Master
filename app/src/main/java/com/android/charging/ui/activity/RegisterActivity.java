package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.util.ToastUtil;
import com.android.charging.util.ZhengZe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;
    @BindView(R.id.img_show_hide_psw)
    public ImageView imgShowHidePsw;
    @BindView(R.id.ed_user_phone)
    public EditText edUserPhone;
    @BindView(R.id.ed_user_password)
    public EditText edUserPassword;
    @BindView(R.id.btn_register)
    public Button btnRegister;
    @BindView(R.id.tv_title)
    public TextView tvTitle;

    private boolean isPswShowHide = false;          //控制显示隐藏密码

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, RegisterActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("注册");
    }

    @OnClick({R.id.img_back, R.id.img_show_hide_psw, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.img_show_hide_psw:
                if (isPswShowHide) {
                    //显示,去隐藏
                    isPswShowHide = false;
                    imgShowHidePsw.setImageResource(R.mipmap.icon_hide_psw);
                    edUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edUserPassword.setSelection(edUserPassword.getText().toString().length());
                } else {
                    //隐藏,去显示
                    isPswShowHide = true;
                    imgShowHidePsw.setImageResource(R.mipmap.icon_show_psw);
                    edUserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edUserPassword.setSelection(edUserPassword.getText().toString().length());
                }
                break;
            case R.id.btn_register:
                if (ZhengZe.isMobileNO(edUserPhone.getText().toString())) {
                    this.showLoading();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            RegisterActivity.this.dismissLoading();
                            startActivity(AuthCodeActivity.newIntent(RegisterActivity.this, edUserPhone.getText().toString()));
                            RegisterActivity.this.finish();
                        }
                    },3000);
                } else {
                    ToastUtil.showShortToast(this, "请输入正确的手机号");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}