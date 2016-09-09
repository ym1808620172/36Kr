package com.sunhongxu.a36kr.controler.activity;

import android.os.AsyncTask;

import com.sunhongxu.a36kr.R;

/**
 * 欢迎界面
 */

public class WelcomeActivity extends AbsBaseActivity {


    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initDatas() {

    }

    /**
     * 创建异步任务
     */
    private class WelcomeAsync extends AsyncTask<Integer, Integer, Void> {


        @Override
        protected Void doInBackground(Integer... params) {
            int all = params[0];
            int num = 0;
            while (all>num){
                all--;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
