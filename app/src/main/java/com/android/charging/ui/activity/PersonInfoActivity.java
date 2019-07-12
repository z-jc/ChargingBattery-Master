package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\20 0020 18:39
 * @class describe
 */
public class PersonInfoActivity extends BaseActivity {


    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.iv_header_photo)
    public ImageView ivHeaderPhoto;
    @BindView(R.id.tv_nickname)
    public TextView tvNickname;
    @BindView(R.id.tv_gender)
    public TextView tvGender;
    @BindView(R.id.tv_address)
    public TextView tvAddress;
    @BindView(R.id.btn_change_psw)
    public Button btnChangePsw;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, PersonInfoActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persion_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("个人信息");

        Glide.with(this).load(R.mipmap.icon_head_photo)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(ivHeaderPhoto);
    }

    @OnClick({R.id.img_back, R.id.btn_change_psw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.btn_change_psw:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
