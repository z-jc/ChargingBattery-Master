package com.android.charging.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.bean.OrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\19 0019 8:59
 * @class describe
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {

    private Context mContext;

    private List<OrderBean> logBeanList = null;

    public OrderAdapter(Context context) {
        this.mContext = context;
        logBeanList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_order, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder viewHolder, int position) {
        OrderBean dataBean = logBeanList.get(position);
        viewHolder.tvMoney.setText(dataBean.getMoney());
        viewHolder.tvTime.setText(dataBean.getTime());
        viewHolder.tvHl.setText(dataBean.getHl());
        viewHolder.tvDeviceId.setText(dataBean.getDeviceId());
        viewHolder.tvOrder.setText(dataBean.getOrder());
        viewHolder.tvStartTime.setText(dataBean.getStartTime());
        viewHolder.tvEndTime.setText(dataBean.getEndTime());
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
    public void setData(List<OrderBean> list) {
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
    public void addData(List<OrderBean> list) {
        this.logBeanList.addAll(0, list);
        this.notifyDataSetChanged();
    }

    /**
     * 清除数据
     */
    public void clear() {
        if (this.logBeanList != null) {
            if (this.logBeanList.size() > 0) {
                this.logBeanList.clear();
            }
        }
        this.notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView tvMoney;
        public TextView tvTime;
        public TextView tvHl;
        public TextView tvDeviceId;
        public TextView tvOrder;
        public TextView tvStartTime;
        public TextView tvEndTime;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvMoney = itemView.findViewById(R.id.tv_money);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvHl = itemView.findViewById(R.id.tv_hl);
            tvDeviceId = itemView.findViewById(R.id.tv_device_id);
            tvOrder = itemView.findViewById(R.id.tv_order);
            tvStartTime = itemView.findViewById(R.id.tv_start_time);
            tvEndTime = itemView.findViewById(R.id.tv_end_time);
        }
    }
}