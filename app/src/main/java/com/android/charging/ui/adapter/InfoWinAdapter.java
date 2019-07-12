package com.android.charging.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.amap.api.maps.AMap;
import com.amap.api.maps.model.Marker;
import com.android.charging.R;
import com.android.charging.app.ChargingApp;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\21 0021 16:34
 * @class describe
 */
public class InfoWinAdapter implements AMap.InfoWindowAdapter{
    private Context mContext = ChargingApp.context;
    private TextView tvContent;
    private String agentName;

    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view = initView();
        return view;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
        agentName = marker.getTitle();
    }

    @NonNull
    private View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_marker, null);
        tvContent = (TextView) view.findViewById(R.id.tv_content);
        tvContent.setText(agentName);
        return view;
    }

}