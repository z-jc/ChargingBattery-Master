package com.android.charging.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.ui.activity.NearActActivity;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\24 0024 9:55
 * @class describe
 */
public class MyWalletExclusiveFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvMoney;           //显示金额
    private Button btnPay;              //提交
    private Button btnWithdraw;         //提现
    private TextView tvDetail;          //钱包明细
    private RelativeLayout rlHaveMoney; //有钱包
    private TextView tvJoin;            //去参加
    private LinearLayout llNoHaveMoney; //没有钱包

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_wallet_exclusive, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {

        tvMoney = (TextView) view.findViewById(R.id.tv_money);
        btnPay = (Button) view.findViewById(R.id.btn_pay);
        btnPay.setOnClickListener(this);
        btnWithdraw = (Button) view.findViewById(R.id.btn_withdraw);
        btnWithdraw.setOnClickListener(this);
        tvDetail = (TextView) view.findViewById(R.id.tv_detail);
        tvDetail.setOnClickListener(this);
        rlHaveMoney = (RelativeLayout) view.findViewById(R.id.rl_have_money);
        tvJoin = (TextView) view.findViewById(R.id.tv_join);
        tvJoin.setOnClickListener(this);
        llNoHaveMoney = (LinearLayout) view.findViewById(R.id.ll_no_have_money);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:

                break;
            case R.id.btn_withdraw:

                break;
            case R.id.tv_join:
                startActivity(NearActActivity.newIntent(getActivity()));
                getActivity().finish();
                break;
            case R.id.tv_detail:

                break;
        }
    }
}