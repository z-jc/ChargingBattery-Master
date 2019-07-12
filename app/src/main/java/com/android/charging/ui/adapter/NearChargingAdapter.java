package com.android.charging.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.bean.NearChargingBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\19 0019 8:59
 * @class describe
 */
public class NearChargingAdapter extends RecyclerView.Adapter<NearChargingAdapter.MyHolder> {

    private Context mContext;

    private List<NearChargingBean> logBeanList = null;

    public NearChargingAdapter(Context context) {
        this.mContext = context;
        logBeanList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_near_charging, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder viewHolder, int position) {
        NearChargingBean dataBean = logBeanList.get(position);
        viewHolder.tvTitle.setText(dataBean.title);
        viewHolder.tvName.setText(dataBean.name);
        viewHolder.tvIdleNumber.setText(dataBean.idleNumber);
        viewHolder.tvFullNumber.setText(dataBean.fulNumber);
        viewHolder.tvSumNumber.setText(dataBean.sumNumber);
        viewHolder.tvTotal.setText(dataBean.total);
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
    public void setData(List<NearChargingBean> list) {
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
    public void addData(List<NearChargingBean> list) {
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
        public TextView tvTitle;
        public TextView tvName;
        public TextView tvIdleNumber;
        public TextView tvFullNumber;
        public TextView tvSumNumber;
        public TextView tvTotal;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvName = itemView.findViewById(R.id.tv_name);
            tvIdleNumber = itemView.findViewById(R.id.tv_idle_number);
            tvFullNumber = itemView.findViewById(R.id.tv_full_number);
            tvSumNumber = itemView.findViewById(R.id.tv_sum_number);
            tvTotal = itemView.findViewById(R.id.tv_total);
        }
    }
}