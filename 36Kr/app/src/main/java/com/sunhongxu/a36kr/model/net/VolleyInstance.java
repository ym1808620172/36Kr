package com.sunhongxu.a36kr.model.net;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sunhongxu.a36kr.controler.app.KrApp;

/**
 * Created by dllo on 16/9/12.
 * Volley的基类
 */
public class VolleyInstance {
    private static VolleyInstance instance;
    private RequestQueue queue;

    //私有化构造方法
    private VolleyInstance() {
        queue = Volley.newRequestQueue(KrApp.getContext());
    }

    //对外提供get方法
    //双重检验锁:单例
    public static VolleyInstance getInstance() {
        if (instance == null) {
            synchronized (VolleyInstance.class) {
                if (instance == null) {
                    instance = new VolleyInstance();
                }
            }
        }
        return instance;
    }

    //开始网络请求
    public void startInstance(String URL, final VolleyRequest request) {
        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                request.success(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                request.failure();
            }
        });
        queue.add(stringRequest);
    }

}
