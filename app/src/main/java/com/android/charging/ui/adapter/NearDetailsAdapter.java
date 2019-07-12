package com.android.charging.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.bean.NearActBean;
import com.android.charging.bean.NearDetailsBean;
import com.android.charging.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\19 0019 8:59
 * @class describe
 */
public class NearDetailsAdapter extends RecyclerView.Adapter<NearDetailsAdapter.MyHolder> {

    private Context mContext;

    private List<NearDetailsBean> logBeanList = null;

    public NearDetailsAdapter(Context context) {
        this.mContext = context;
        logBeanList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_near_details, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder viewHolder, int position) {
        NearDetailsBean bean = logBeanList.get(position);
        viewHolder.tvMoney.setText(bean.money);
        viewHolder.tvName.setText(bean.name);
        viewHolder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShortToast(mContext, "去充值");
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
    public void setData(List<NearDetailsBean> list) {
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
    public void addData(List<NearDetailsBean> list) {
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
        public TextView tvName;
        public Button btnPay;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvMoney = itemView.findViewById(R.id.tv_money);
            tvName = itemView.findViewById(R.id.tv_name);
            btnPay = itemView.findViewById(R.id.btn_pay);
        }
    }
}