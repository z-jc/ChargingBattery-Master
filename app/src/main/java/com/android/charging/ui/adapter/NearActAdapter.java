package com.android.charging.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.bean.NearActBean;
import com.android.charging.bean.NearChargingBean;
import com.android.charging.bean.NearDetailsBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\19 0019 8:59
 * @class describe
 */
public class NearActAdapter extends RecyclerView.Adapter<NearActAdapter.MyHolder> {


    private Context mContext;

    private List<NearActBean> logBeanList = null;
    private NearDetailsAdapter adapter;

    public NearActAdapter(Context context) {
        this.mContext = context;
        logBeanList = new ArrayList<>();
        adapter = new NearDetailsAdapter(this.mContext);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_near_act, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder viewHolder, int position) {
        NearActBean bean = logBeanList.get(position);
        viewHolder.tvTitle.setText(bean.title);
        viewHolder.tvTime.setText(bean.time);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;//禁止滑动
            }
        };
        viewHolder.recyclerViewDetails.setLayoutManager(layoutManager);
        viewHolder.recyclerViewDetails.setAdapter(adapter);
        adapter.setData(getListData());
        viewHolder.tvDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(getClass().getSimpleName(), "当前显示状态:" + viewHolder.recyclerViewDetails.getVisibility() + "," + viewHolder.tvDetails.getText().toString());
                if (viewHolder.recyclerViewDetails.getVisibility() == View.VISIBLE) {
                    // TODO: 2019\6\22 0022 已显示,去隐藏
                    viewHolder.tvDetails.setText("详情");
                    viewHolder.ivDetails.setImageResource(R.mipmap.icon_you);
                    viewHolder.recyclerViewDetails.setVisibility(View.GONE);
                } else if (viewHolder.recyclerViewDetails.getVisibility() == View.GONE) {
                    // TODO: 2019\6\22 0022 已隐藏,去显示
                    viewHolder.tvDetails.setText("收起");
                    viewHolder.ivDetails.setImageResource(R.mipmap.icon_xia);
                    viewHolder.recyclerViewDetails.setVisibility(View.VISIBLE);
                }
            }
        });

        viewHolder.ivDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(getClass().getSimpleName(), "当前显示状态:" + viewHolder.recyclerViewDetails.getVisibility() + "," + viewHolder.tvDetails.getText().toString());
                if (viewHolder.recyclerViewDetails.getVisibility() == View.VISIBLE) {
                    // TODO: 2019\6\22 0022 已显示,去隐藏
                    viewHolder.tvDetails.setText("详情");
                    viewHolder.ivDetails.setImageResource(R.mipmap.icon_you);
                    viewHolder.recyclerViewDetails.setVisibility(View.GONE);
                } else if (viewHolder.recyclerViewDetails.getVisibility() == View.GONE) {
                    // TODO: 2019\6\22 0022 已隐藏,去显示
                    viewHolder.tvDetails.setText("收起");
                    viewHolder.ivDetails.setImageResource(R.mipmap.icon_xia);
                    viewHolder.recyclerViewDetails.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public List<NearDetailsBean> getListData() {
        NearDetailsBean detailsBean1 = new NearDetailsBean();
        detailsBean1.setName("赠送4元充电红包");
        detailsBean1.setMoney("￥40");
        NearDetailsBean detailsBean2 = new NearDetailsBean();
        detailsBean2.setName("赠送50元充电红包");
        detailsBean2.setMoney("￥500");
        List<NearDetailsBean> list = new ArrayList<>();
        list.add(detailsBean1);
        list.add(detailsBean2);
        return list;
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
    public void setData(List<NearActBean> list) {
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
    public void addData(List<NearActBean> list) {
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
        public TextView tvTime;
        public TextView tvDetails;
        public ImageView ivDetails;
        public RecyclerView recyclerViewDetails;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvDetails = itemView.findViewById(R.id.tv_details);
            ivDetails = itemView.findViewById(R.id.iv_details);
            recyclerViewDetails = itemView.findViewById(R.id.recycler_view_details);
        }
    }
}