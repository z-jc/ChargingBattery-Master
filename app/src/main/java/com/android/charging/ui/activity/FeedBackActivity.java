package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.ui.adapter.NewMsgAdapter;
import com.android.charging.util.EdittorUtil;
import com.android.charging.util.ToastUtil;
import com.android.charging.util.ZhengZe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 10:29
 * @class describe
 */
public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.img_back)
    public ImageView ivBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;
    @BindView(R.id.ed_content)
    public EditText edContent;
    @BindView(R.id.tv_content_size)
    public TextView tvContentSize;
    @BindView(R.id.ed_phone)
    public EditText edPhone;
    @BindView(R.id.btn_submit)
    public Button btnSubmit;
    @BindView(R.id.tv_feed_record)
    public TextView tvFeedRecord;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, FeedBackActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("意见反馈");

        edContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().length();
                length = 200 - length;
                tvContentSize.setText(length + "/200");
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.btn_submit, R.id.tv_feed_record, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                String phone = edPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtil.showShortToast(this, "请输入手机号");
                    return;
                }
                if (!ZhengZe.isMobileNO(phone)) {
                    ToastUtil.showShortToast(this, "请输入正确的手机号:" + phone);
                    return;
                }
                break;
            case R.id.tv_feed_record:
                break;
            case R.id.img_back:
                this.finish();
                break;
        }
    }
}
