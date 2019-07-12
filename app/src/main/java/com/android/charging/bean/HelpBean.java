package com.android.charging.bean;

/**
 * @author Administrator
 * @particulars
 * @time 2019\6\24 0024 15:03
 * @class describe
 */
public class HelpBean extends BaseBean {

    public String title;
    public String content;

    public HelpBean(String title,String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HelpBean{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
