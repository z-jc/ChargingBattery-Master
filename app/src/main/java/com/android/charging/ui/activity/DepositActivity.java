package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.ui.view.CashierInputFilter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 10:29
 * @class describe
 */
public class DepositActivity extends BaseActivity {

    @BindView(R.id.img_back)
    public ImageView ivBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;
    @BindView(R.id.ed_limit)
    public EditText edLimit;
    @BindView(R.id.ed_name)
    public EditText edName;
    @BindView(R.id.ed_tel_phone)
    public EditText edTelPhone;
    @BindView(R.id.ed_money)
    public EditText edMoney;
    @BindView(R.id.btn_deposit)
    public Button btnDeposit;
    @BindView(R.id.tv_deposit)
    public TextView tvDeposit;
    @BindView(R.id.tv_notice)
    public TextView tvNotice;

    private StringBuilder stringBuilder = new StringBuilder();

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, DepositActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("钱包提现");

        stringBuilder.append("注:提现额度=可用额度-赠送额度" + "\n");
        stringBuilder.append("温馨提示:" + "\n");
        stringBuilder.append("1.为防止恶意操作发生,所以充值的资金,15天内不可提现" + "\n");

        //改变字体颜色
        //先构造SpannableString
        SpannableString spanString = new SpannableString(stringBuilder.toString());
        //再构造一个改变字体颜色的Span
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        //将这个Span应用于指定范围的字体
        spanString.setSpan(span, 0, 16, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvNotice.setText(spanString);

        /**
         * 设置额度不可编辑
         */
        edLimit.setFocusable(false);
        edLimit.setFocusableInTouchMode(false);
        InputFilter[] inputFilters = {new CashierInputFilter()};
        edMoney.setFilters(inputFilters);
        edMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith(".")) {
                    edMoney.setText("");
                    return;
                }
                if (edMoney.getText().toString().indexOf(".") >= 0) {
                    if (edMoney.getText().toString().indexOf(".", edMoney.getText().toString().indexOf(".") + 1) > 0) {
                        edMoney.setText(edMoney.getText().toString().substring(0, edMoney.getText().toString().length() - 1));
                        edMoney.setSelection(edMoney.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String data = s.toString();
                Log.e(TAG, "s:" + data);
                if (TextUtils.isEmpty(data)) {
                    return;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.img_back, R.id.btn_deposit, R.id.tv_deposit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                this.finish();
                break;
            case R.id.btn_deposit:
                break;
            case R.id.tv_deposit:
                break;
        }
    }
}
