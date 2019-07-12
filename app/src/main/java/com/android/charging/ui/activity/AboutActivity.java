package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 10:29
 * @class describe
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.img_back)
    public ImageView ivBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("关于我们");
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
