package com.android.charging.http;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * author cowards
 * created on 2019\3\24 0024
 **/
public class XUtilsManager {

    private static volatile XUtilsManager instance = null;
    private static String TAG = getInstance().getClass().getSimpleName();

    public static XUtilsManager getInstance() {
        if (instance == null) {
            synchronized (XUtilsManager.class) {
                if (instance == null) {
                    instance = new XUtilsManager();
                }
            }
        }
        return instance;
    }

    /**
     * 下载文件
     *
     * @param what         标签
     * @param httpCallBack
     * @param filePath     下载到的本地文件路径
     * @param httpUrl      文件网络地址
     */
    public void getDownLoad(final int what, final String filePath, String httpUrl, final HttpCallBack httpCallBack) {
        RequestParams params = new RequestParams(httpUrl);
        params.setSaveFilePath(filePath);
        params.setAutoRename(false);//自动为文件命名
        params.setAutoResume(true);//自动断点续传
        x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onSuccess(File result) {
                httpCallBack.onResponse(what, result.getAbsolutePath());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                httpCallBack.onFailure(what, ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.e(TAG, "取消下载...");
            }

            @Override
            public void onFinished() {
                Log.e(TAG, "下载结束...");
                httpCallBack.onEndDownload(what, filePath);
            }

            //网络请求之前回调
            @Override
            public void onWaiting() {
                Log.e(XUtilsManager.class.getSimpleName(), "下载等待中...");
            }

            //网络请求开始的时候回调
            @Override
            public void onStarted() {
                Log.e(TAG, "开始下载...");
                httpCallBack.onStartDownload(what, "开始下载");
            }

            //下载的时候不断回调的方法
            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                Log.e(TAG, "当前已下载：" + current / 1000 / 1000.00 + ".MB,总大小：" + total / 1000 / 1000.00 + ".MB");
                int schedule = (int) Math.round((double) current / (double) total * 100);
                httpCallBack.onSchedule(what, schedule, String.valueOf(total / 1000 / 1000.00));
            }
        });
    }

    public interface HttpCallBack {

        /**
         * 下载结束
         */
        void onEndDownload(int what, String end);

        /**
         * 开始结束
         */
        void onStartDownload(int what, String start);//开始下载

        /**
         * 下载进度
         *
         * @param what
         * @param schedule 当前下载百分比
         * @param size     apk文件总大小 单位:M
         */
        void onSchedule(int what, int schedule, String size);//进度

        /**
         * 访问成功回调接口
         */
        void onResponse(int what, String response);

        /**
         * 访问成错误回调接口
         */
        void onFailure(int what, String error);

        /**
         * 请求结束
         */
        void onFinshed();
    }
}