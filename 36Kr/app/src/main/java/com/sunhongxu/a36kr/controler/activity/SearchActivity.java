package com.sunhongxu.a36kr.controler.activity;


import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.adapter.NewsAllAdapter;
import com.sunhongxu.a36kr.controler.adapter.SearchAdapter;
import com.sunhongxu.a36kr.model.bean.NewsAllBean;
import com.sunhongxu.a36kr.model.bean.SearchBean;
import com.sunhongxu.a36kr.model.net.VolleyInstance;
import com.sunhongxu.a36kr.model.net.VolleyRequest;
import com.sunhongxu.a36kr.utils.NetConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 搜索界面
 */

public class SearchActivity extends AbsBaseActivity implements View.OnClickListener {


    private LinearLayout linearLayout;//定义界面布局
    private ImageView searchImg;//定义搜索图片
    private TextView searchTv;//定义取消的Tv
    private EditText searchEt;//定义Et
    private SearchAdapter searchAdapter;//定义适配器
    private ListView listView;//定义ListView
    private LinearLayout searchLl;//定义无搜索记录的布局
    private boolean isHave = false;//定义是否有数据
    //自动更新进度任务
    private Runnable changeRunnable;
    //发布任务的消息处理者
    private Handler handler;//引包 android.os
    private PopupWindow popupWindow;
    private String etContent;
    private LinearLayout serachEtLl;
    private NewsAllAdapter newsAllAdapter;
    private boolean startSearch = false;
    private TextView searchHistoryTv;

    //绑定布局
    @Override
    protected int setLayout() {
        return R.layout.activity_search;
    }

    //初始化组件并设置监听
    @Override
    protected void initView() {
        linearLayout = byView(R.id.search_root_ll);
        searchImg = byView(R.id.search_aty_img);
        searchEt = byView(R.id.search_aty_et);
        searchTv = byView(R.id.search_aty_tv_cancel);
        searchImg.setOnClickListener(this);
        searchTv.setOnClickListener(this);
        listView = byView(R.id.search_listview);
        searchLl = byView(R.id.search_no_ll);
        serachEtLl = byView(R.id.search_et_ll);
        searchHistoryTv = byView(R.id.history_search_tv);
    }

    //加载数据
    @Override
    protected void initDatas() {
        //设置布局内边距的上边距为电量栏高度
        linearLayout.setPadding(0, MarginTop(), 0, 0);
        //定义Sp
        SharedPreferences preferences = getSharedPreferences("text", MODE_PRIVATE);
        //取值,如果没有值将isHave设为默认值false
        isHave = preferences.getBoolean("ishave", false);
        //如果isHave为true说明有搜索记录,将搜索记录设置给设配器
        if (isHave) {
            //定义设配器并绑定
            searchAdapter = new SearchAdapter(this);
            listView.setAdapter(searchAdapter);
            //将sp里的值全部取出
            Map<String, ?> a = preferences.getAll();
            //将sp里的Map类型的值转为List型数组
            List<String> mapKeyList = new ArrayList(a.keySet());
//            定义一个List型数组
            List<SearchBean> datas = new ArrayList<>();
            //用一个循环,0到List数组的长度,将数据添加到List<SearchBean>里
            for (int i = 0; i < mapKeyList.size(); i++) {
                //将ishave数据移除数组
                mapKeyList.remove("ishave");
                SearchBean sb = new SearchBean();
                //设置数据
                sb.setContent(mapKeyList.get(i));
                datas.add(sb);
            }
            searchAdapter.setDatas(datas);
            //将没有搜索记录的去掉
            searchLl.setVisibility(View.GONE);
        }
        //添加清理文字的尾布局,并设监听
        addFooter();
        //实例化handler
        handler = new Handler();
        //搜索的设置,弹出Pop
        searchPop();
        //启动任务
        //1秒之后启动任务
        handler.postDelayed(changeRunnable, 1000);
    }

    private void searchPop() {
        changeRunnable = new Runnable() {
            @Override
            public void run() {
                etContent = searchEt.getText().toString();
                if (!etContent.isEmpty() && !startSearch) {
                    popupWindow = new PopupWindow();
                    popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                    popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
                    final View view = getLayoutInflater().inflate(R.layout.item_search_list_pop, null);
                    final ListView PopList = (ListView) view.findViewById(R.id.item_search_list_pop);
                    newsAllAdapter = new NewsAllAdapter(SearchActivity.this, null);
                    PopList.setAdapter(newsAllAdapter);
                    VolleyInstance.getInstance().startInstance(NetConstants.SEARCHNET + etContent + NetConstants.SEARCHNETEND, new VolleyRequest() {
                        @Override
                        public void success(String result) {
                            Gson gson = new Gson();
                            NewsAllBean allBean = gson.fromJson(result, NewsAllBean.class);
                            List<NewsAllBean.DataBean.DataBeans> dataBeanses = allBean.getData().getData();
                            if (dataBeanses.size() > 0) {
                                if (dataBeanses.size() > 3) {
                                    newsAllAdapter.setDatas(dataBeanses.subList(0, 3)
                                    );
                                } else {
                                    newsAllAdapter.setDatas(dataBeanses);
                                }
                                PopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Log.d("aaa", "点击了");
                                    }
                                });
                                startSearch = true;
                                popupWindow.setContentView(view);
                                popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, 0, MarginTop() + serachEtLl.getHeight());
                            } else {
                                listView.setVisibility(View.GONE);
                                searchHistoryTv.setText("搜索无结果");
                            }
                        }

                        @Override
                        public void failure() {

                        }
                    });

                }
                if (etContent.isEmpty()&&startSearch) {
                    popupWindow.dismiss();
                }
                handler.postDelayed(changeRunnable, 1000);
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!etContent.isEmpty()) {
            handler.removeCallbacks(changeRunnable);
            searchEt.getText().clear();
            popupWindow.dismiss();
        }
    }

    //添加清理文字的尾布局,并设监听
    private void addFooter() {
        //注入布局
        View view = getLayoutInflater().inflate(R.layout.item_fooder_search, null);
        //初始化取消的Tv并设置监听
        TextView searchClean = (TextView) view.findViewById(R.id.search_clear);
        searchClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将sp清空
                SharedPreferences preferences = getSharedPreferences("text", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                //设置无搜索记录布局为可见
                searchLl.setVisibility(View.VISIBLE);
            }
        });
        listView.addFooterView(view);
    }


    @Override
    public void onClick(View v) {
        //获得Et的内容
        String contentEt = searchEt.getText().toString();
        switch (v.getId()) {
            case R.id.search_aty_img:
                //如果内容是空的Toast
                if (contentEt.isEmpty()) {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                } else {
                    //不为空的话将内容存储到sp里,key值也为内容
                    SharedPreferences preferences = getSharedPreferences("text", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(contentEt, contentEt);
                    //将是否有值存为true
                    editor.putBoolean("ishave", true);
                    editor.commit();
                }
                break;
            case R.id.search_aty_tv_cancel:
                //结束界面
                finish();
                break;

        }
    }

}
