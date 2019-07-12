package com.android.charging.bean;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 9:31
 * @class describe
 */
public class NearActBean extends BaseBean {


    /**
     * 标题
     */
    public String title;

    /**
     * 开始时间和结束时间
     */
    public String time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "NearActBean{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
