package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.ui.adapter.NewMsgAdapter;
import com.android.charging.ui.adapter.WalletRechargeAdapter;
import com.android.charging.ui.view.CashierInputFilter;
import com.android.charging.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 10:29
 * @class describe
 */
public class WalletRechargeActivity extends BaseActivity implements WalletRechargeAdapter.OnLister {

    @BindView(R.id.img_back)
    public ImageView ivBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.ed_other_money)
    public EditText edOtherMoney;
    @BindView(R.id.btn_recharge)
    public Button btnRecharge;
    @BindView(R.id.tv_recharge_record)
    public TextView tvRechargeRecord;

    private WalletRechargeAdapter adapter;
    private List<String> list;

    private volatile double money = 0.00;//要重置的金额

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, WalletRechargeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_recharge);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("钱包充值");

        GridLayoutManager layoutManager = new GridLayoutManager(this, 3) {
            @Override
            public boolean canScrollVertically() {
                return false;//禁止滚动
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WalletRechargeAdapter(this, this);
        recyclerView.setAdapter(adapter);
        adapter.addData(getList());
        InputFilter[] inputFilters = {new CashierInputFilter()};
        edOtherMoney.setFilters(inputFilters);
        edOtherMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().startsWith(".")) {
                    edOtherMoney.setText("");
                    return;
                }
                if (edOtherMoney.getText().toString().indexOf(".") >= 0) {
                    if (edOtherMoney.getText().toString().indexOf(".", edOtherMoney.getText().toString().indexOf(".") + 1) > 0) {
                        edOtherMoney.setText(edOtherMoney.getText().toString().substring(0, edOtherMoney.getText().toString().length() - 1));
                        edOtherMoney.setSelection(edOtherMoney.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String data = s.toString();
                Log.e(TAG, "s:" + data);
                if (TextUtils.isEmpty(data)) {
                    money = 0.00;
                    return;
                }
                money = Double.parseDouble(data);
                adapter.clear();
            }
        });
    }

    @Override
    public void onLister(int position) {
        if (edOtherMoney.getText().toString().length() > 0) {
            edOtherMoney.setText("");
        }
        this.money = Double.parseDouble(this.list.get(position));
    }

    private List<String> getList() {
        list = new ArrayList<>();
        list.add("20");
        list.add("30");
        list.add("50");
        list.add("100");
        list.add("200");
        list.add("300");
        return list;
    }

    @OnClick({R.id.btn_recharge, R.id.tv_recharge_record, R.id.img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recharge:
                if (this.money <= 0.00) {
                    ToastUtil.showShortToast(this, "当前没有选择充值多少钱");
                    return;
                }
                ToastUtil.showShortToast(this, "准备充值:" + this.money + "元");
                break;
            case R.id.tv_recharge_record:
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
