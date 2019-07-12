package com.android.charging.app;

import android.app.Application;
import android.content.Context;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\18 0018 10:23
 * @class describe
 */
public class ChargingApp extends Application {

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getContext();
        initTest();
    }

    private void initTest() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    public Context getContext() {
        return this.getApplicationContext();
    }
}