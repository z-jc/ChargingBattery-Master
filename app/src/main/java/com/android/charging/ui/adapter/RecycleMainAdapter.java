package com.android.charging.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.bean.MenuBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\19 0019 8:59
 * @class describe
 */
public class RecycleMainAdapter extends RecyclerView.Adapter<RecycleMainAdapter.MyHolder> {

    private Context mContext;
    private List<MenuBean> logBeanList = null;
    private Lister lister;

    public RecycleMainAdapter(Context context, Lister lister) {
        this.mContext = context;
        logBeanList = new ArrayList<>();
        this.lister = lister;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_menu, viewGroup, false);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder viewHolder, final int position) {
        MenuBean bean = logBeanList.get(position);
        viewHolder.tvItemMenu.setText(bean.title);
        viewHolder.imgLeft.setImageResource(bean.drawable);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lister != null) {
                    lister.onClick(position);
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
    public void setData(List<MenuBean> list) {
        if (this.logBeanList != null) {
            if (this.logBeanList.size() > 0) {
                this.logBeanList.clear();
            }
        }
        this.logBeanList.addAll(list);
        this.notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private ImageView imgLeft;
        private TextView tvItemMenu;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imgLeft = itemView.findViewById(R.id.img_left);
            tvItemMenu = itemView.findViewById(R.id.tv_item_menu);
        }
    }

    public interface Lister {
        void onClick(int position);
    }
}