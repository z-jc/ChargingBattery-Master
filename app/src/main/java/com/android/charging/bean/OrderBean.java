package com.android.charging.bean;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 17:45
 * @class describe
 */
public class OrderBean extends BaseBean {

    /**
     * 充电金额
     */
    public String money;

    /**
     * 充电时长
     */
    public String time;

    /**
     * 充电回路
     */
    public String hl;

    /**
     * 设备编号
     */
    public String deviceId;

    /**
     * 订单编号
     */
    public String order;

    /**
     * 开始时间
     */
    public String startTime;

    /**
     * 结束时间
     */
    public String endTime;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "money='" + money + '\'' +
                ", time='" + time + '\'' +
                ", hl='" + hl + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", order='" + order + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}