package com.xiangke.bean;

/**
 * @Date:2022/2/25 14:33
 * @Author:dengjin
 * @Name:
 */
public class PageTitleBean {
    public PageTitleBean() {
    }

    public PageTitleBean(String title, String count) {
        this.title = title;
        this.count = count;
    }

    private String title;
    private String count;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
