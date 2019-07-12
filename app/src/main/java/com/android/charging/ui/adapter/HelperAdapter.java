package com.android.charging.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.bean.HelpBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\19 0019 8:59
 * @class describe
 */
public class HelperAdapter extends RecyclerView.Adapter<HelperAdapter.MyHolder> {

    private Context mContext;
    private List<HelpBean> logBeanList = null;
    private List<Integer> positionList = null;

    public HelperAdapter(Context context) {
        this.mContext = context;
        logBeanList = new ArrayList<>();
        positionList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_help, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder viewHolder, final int position) {
        final HelpBean bean = logBeanList.get(position);
        viewHolder.tvTitle.setText(bean.title);
        viewHolder.tvContent.setText(bean.content);

        if (positionList.contains(position)) {
            viewHolder.tvContent.setVisibility(View.VISIBLE);
            viewHolder.imgRight.setImageResource(R.mipmap.icon_xia);
        } else {
            viewHolder.tvContent.setVisibility(View.GONE);
            viewHolder.imgRight.setImageResource(R.mipmap.icon_you);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(getClass().getSimpleName(), "" + Arrays.toString(positionList.toArray()));
                if (positionList.contains(position)) {
                    viewHolder.imgRight.setImageResource(R.mipmap.icon_you);
                    viewHolder.tvContent.setVisibility(View.GONE);
                    positionList.clear();
                } else {
                    positionList.clear();
                    positionList.add(position);
                    viewHolder.tvContent.setVisibility(View.VISIBLE);
                    viewHolder.imgRight.setImageResource(R.mipmap.icon_xia);
                }
                notifyDataSetChanged();
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
    public void setData(List<HelpBean> list) {
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
    public void addData(List<HelpBean> list) {
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
        public TextView tvTitle;
        public TextView tvContent;
        public ImageView imgRight;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvContent = itemView.findViewById(R.id.tv_content);
            imgRight = itemView.findViewById(R.id.img_right);
        }
    }
}