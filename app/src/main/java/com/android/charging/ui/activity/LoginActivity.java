package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.config.AppConfig;
import com.android.charging.ui.dialog.DialogLoading;
import com.android.charging.util.ToastUtil;
import com.android.charging.util.WXUtil;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\18 0018 10:19
 * @class describe
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.btn_login)
    public Button btnLogin;
    @BindView(R.id.ed_user_phone)
    public EditText edUserPhone;
    @BindView(R.id.ed_user_password)
    public EditText edUserPassword;
    @BindView(R.id.img_wx)
    public ImageView imgWx;
    @BindView(R.id.tv_forget_psw)
    public TextView tvForgetPsw;
    @BindView(R.id.tv_register)
    public TextView tvRegister;

    /**
     * 微信登录相关
     */
    private IWXAPI iwxapi;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        iwxapi = WXAPIFactory.createWXAPI(this, AppConfig.APP_ID_WX, true);
        //将应用的appid注册到微信
        iwxapi.registerApp(AppConfig.APP_ID_WX);
    }

    @OnClick({R.id.ed_user_phone, R.id.ed_user_password, R.id.img_wx, R.id.tv_forget_psw, R.id.tv_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ed_user_phone:
                break;
            case R.id.ed_user_password:
                break;
            case R.id.img_wx:
                if (!WXUtil.isWeixinAvilible(LoginActivity.this)) {
                    ToastUtil.showShortToast(this, "请先安装微信");
                    return;
                }
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                //req.scope = "snsapi_login";//提示 scope参数错误，或者没有scope权限
                req.state = "wechat_sdk_微信登录";
                iwxapi.sendReq(req);
                break;
            case R.id.tv_forget_psw:
                startActivity(ForgetPswActivity.newIntent(LoginActivity.this));
                break;
            case R.id.tv_register:
                startActivity(RegisterActivity.newIntent(LoginActivity.this));
                break;
            case R.id.btn_login:
                this.showLoading();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        LoginActivity.this.dismissLoading();
                        startActivity(MainActivity.newIntent(LoginActivity.this));
                        finish();
                    }
                }, 3000);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            String headUrl = data.getStringExtra("headUrl");
            Log.e(TAG, "url:" + headUrl);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}