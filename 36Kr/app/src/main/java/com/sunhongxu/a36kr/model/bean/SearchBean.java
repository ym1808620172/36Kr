package com.sunhongxu.a36kr.model.bean;

/**
 * Created by dllo on 16/9/12.
 * 搜索界面的实体类
 */
public class SearchBean {
    private String content;

    public SearchBean(String content) {
        this.content = content;
    }

    public SearchBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
