package com.sunhongxu.a36kr.model.net;

/**
 * Created by dllo on 16/9/12.
 * Volley请求数据的接口
 */
public interface VolleyRequest {
    //数据请求成功的方法
    void success(String result);

    //数据请求失败的方法
    void failure();
}
