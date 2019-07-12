package com.android.charging.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.bean.NewMsgBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\19 0019 8:59
 * @class describe
 */
public class WalletRechargeAdapter extends RecyclerView.Adapter<WalletRechargeAdapter.MyHolder> {

    private Context mContext;
    private List<String> logBeanList = null;
    private List<Integer> positionList = null;
    private OnLister lister;

    public WalletRechargeAdapter(Context context, OnLister lister) {
        this.mContext = context;
        this.lister = lister;
        logBeanList = new ArrayList<>();
        positionList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_wallet_recharge, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder viewHolder, final int position) {
        final String money = logBeanList.get(position);
        viewHolder.tvMoney.setText(money + "元");
        if (positionList.contains(position)) {
            viewHolder.tvMoney.setTextColor(0xFFFFFFFF);
            viewHolder.tvMoney.setBackgroundResource(R.color.bule4);
            viewHolder.tvMoney.setEnabled(false);//不可点击
        } else {
            viewHolder.tvMoney.setTextColor(0xFF0A94EA);
            viewHolder.tvMoney.setBackgroundResource(R.color.write);
            viewHolder.tvMoney.setEnabled(true);//不可点击
        }

        viewHolder.tvMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(getClass().getSimpleName(), "" + Arrays.toString(positionList.toArray()));
                if (positionList.contains(position)) {
                    viewHolder.tvMoney.setEnabled(true);//不可点击
                    Log.e(getClass().getSimpleName(), "不可点击");
                } else {
                    positionList.clear();
                    positionList.add(position);
                    viewHolder.tvMoney.setEnabled(false);//可点击
                    Log.e(getClass().getSimpleName(), "可点击");
                }

                viewHolder.tvMoney.setTextColor(0xFFFFFFFF);
                viewHolder.tvMoney.setBackgroundResource(R.color.bule4);
                if (lister != null) {
                    lister.onLister(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return logBeanList != null ? logBeanList.size() : 0;
    }

    /**
     * 刷新数据
     *
     * @param list
     */
    public void setData(List<String> list) {
        if (this.logBeanList != null) {
            if (this.logBeanList.size() > 0) {
                this.logBeanList.clear();
            }
        }
        this.logBeanList.addAll(list);
        this.notifyDataSetChanged();
    }

    /**
     * 添加数据
     *
     * @param list
     */
    public void addData(List<String> list) {
        this.logBeanList.addAll(0, list);
        this.notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        if (this.positionList != null) {
            if (this.positionList.size() > 0) {
                this.positionList.clear();
            }
        }
        this.notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvMoney;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvMoney = itemView.findViewById(R.id.tv_money);
        }
    }

    public interface OnLister {
        void onLister(int position);
    }
}