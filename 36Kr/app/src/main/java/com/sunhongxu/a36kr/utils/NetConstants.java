package com.sunhongxu.a36kr.utils;

/**
 * Created by dllo on 16/9/14.
 * 全部网址
 */
public final class NetConstants {
    private NetConstants() {
    }

    /**
     * 发现界面网址
     */
    public static final String DISCOVERROTATE = "https://rong.36kr.com/api/mobi/roundpics/v4";//发现界面轮播图网址
    public static final String FINDPEOPLE = "https://rong.36kr.com/api/mobi/investor?page=1&pageSize=20";//寻找投资人网址


    /**
     * 股权投资界面部分网址,用于拼接
     */
    public static final String EQUITYHELPER = "https://rong.36kr.com/api/mobi/cf/actions/list?page=1&type=";//股权投资界面前半部分
    public static final String EQUITYHELPEREND = "&pageSize=20";//股权投资界面后半部分
    /**
     * 新闻界面部分网址,用于拼接
     */
    public static final String NEWSHELPER = "https://rong.36kr.com/api/mobi/news?pageSize=20&columnId=";//新闻界面前半部分
    public static final String NEWSURLEND = "&pagingAction=up";//新闻界面后半部分
    public static final String ROTATEURL = "https://rong.36kr.com/api/mobi/roundpics/v4";//新闻界面轮播图
    /**
     * 新闻详情页网址
     */
    public static final String DETAILSURL = "http://rong.36kr.com/api/mobi/news/";//详情页前半部分
    /**
     * 作者详情页
     */
    public static final String AUTHORREGION = "https://rong.36kr.com/api/mobi/news/";//前半部分
    public static final String AUTHORREGIONEND = "/author-region";//后半部分
    /**
     * 搜索网址
     */
    public static final String SEARCHNET = "https://rong.36kr.com/api/mobi/news/search?keyword=";//前半部分
    public static final String SEARCHNETEND = "&page=1&pageSize=20";//后半部分网址
    /**
     * 视频界面网址:用的是内涵段子...
     */
    public static final String VideoView = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-104";
    /**
     * 近期活动网址
     */
    public static final String RECENTATY = "https://rong.36kr.com/api/mobi/activity/list?page=1&categoryId=";//全部网址
    public static final String RECENTATYEND = "&pageSize=20";//后半部分
}
