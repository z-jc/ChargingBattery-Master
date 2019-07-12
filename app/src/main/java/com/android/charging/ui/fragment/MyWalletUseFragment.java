package com.android.charging.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.ui.activity.DepositActivity;
import com.android.charging.ui.activity.WalletRechargeActivity;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\24 0024 9:55
 * @class describe
 */
public class MyWalletUseFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvMoney;
    private Button btnPay;
    private Button btnWithdraw;
    private TextView tvDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_wallet_use, container, false);
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
            case R.id.btn_pay:          //充值
                startActivity(WalletRechargeActivity.newIntent(getActivity()));
                getActivity().finish();
                break;
            case R.id.btn_withdraw:     //提现
                startActivity(DepositActivity.newIntent(getActivity()));
                getActivity().finish();
                break;
            case R.id.tv_detail:        //钱包明细

                break;
        }
    }
}