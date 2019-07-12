package com.android.charging.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.charging.R;
import com.android.charging.bean.NewMsgBean;
import com.android.charging.ui.adapter.NewMsgAdapter;
import com.github.ybq.android.spinkit.SpinKitView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 17:12
 * @class describe
 */
public class NewMsgActivity extends BaseActivity implements OnLoadMoreListener,
        OnRefreshListener {

    @BindView(R.id.img_back)
    public ImageView imgBack;
    @BindView(R.id.tv_title)
    public TextView tvTitle;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    public SmartRefreshLayout refreshLayout;
    @BindView(R.id.spin_kit)
    public SpinKitView spinKit;
    @BindView(R.id.title)
    public RelativeLayout rlTitle;

    private NewMsgAdapter adapter;

    public static Intent newIntent(Activity activity) {
        Intent intent = new Intent(activity, NewMsgActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_msg);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        rlTitle.setPadding(0, this.height, 0, 0);
        tvTitle.setText("最新消息");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewMsgAdapter(this);
        recyclerView.setAdapter(adapter);

        //设置 Footer 为 球脉冲 样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        NewMsgBean msgBean = new NewMsgBean();
        msgBean.setName("欢迎使用商信充电子科技");
        msgBean.setTitle("欢迎使用商信充电子科技");
        List<NewMsgBean> list = new ArrayList<>();
        list.add(msgBean);
        adapter.setData(list);
    }

    @Override
    public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
        // TODO: 2019\6\22 0022 上拉加载
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }
        }, 1000);
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        // TODO: 2019\6\19 0019 下拉刷新
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.img_back)
    public void onViewClicked() {
        this.finish();
    }
}
