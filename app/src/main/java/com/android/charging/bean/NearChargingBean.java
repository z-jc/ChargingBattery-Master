package com.android.charging.bean;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 9:31
 * @class describe
 */
public class NearChargingBean extends BaseBean {

    /**
     * 标题
     */
    public String title;

    /**
     * 名字
     */
    public String name;

    /**
     * 空闲数量
     */
    public String idleNumber;

    /**
     * 充电数量
     */
    public String fulNumber;

    /**
     * 总数量
     */
    public String sumNumber;

    /**
     * 总里程
     */
    public String total;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdleNumber() {
        return idleNumber;
    }

    public void setIdleNumber(String idleNumber) {
        this.idleNumber = idleNumber;
    }

    public String getFulNumber() {
        return fulNumber;
    }

    public void setFulNumber(String fulNumber) {
        this.fulNumber = fulNumber;
    }

    public String getSumNumber() {
        return sumNumber;
    }

    public void setSumNumber(String sumNumber) {
        this.sumNumber = sumNumber;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "NearChargingBean{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", idleNumber='" + idleNumber + '\'' +
                ", fulNumber='" + fulNumber + '\'' +
                ", sumNumber='" + sumNumber + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
