package com.android.charging.bean;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\22 0022 9:31
 * @class describe
 */
public class NewMsgBean extends BaseBean {

    /**
     * 标题
     */
    public String title;

    /**
     * 消息内容
     */
    public String name;

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

    @Override
    public String toString() {
        return "NewMsgBean{" +
                "title='" + title + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
