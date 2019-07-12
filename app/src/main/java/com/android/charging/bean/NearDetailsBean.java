package com.android.charging.bean;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 9:31
 * @class describe
 */
public class NearDetailsBean extends BaseBean {

    /**
     * 金额
     */
    public String money;

    /**
     * 活动名称
     */
    public String name;

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NearDetailsBean{" +
                "money='" + money + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
