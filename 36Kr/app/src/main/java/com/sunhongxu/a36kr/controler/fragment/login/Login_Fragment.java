package com.sunhongxu.a36kr.controler.fragment.login;

import android.os.Bundle;

import com.sunhongxu.a36kr.R;
import com.sunhongxu.a36kr.controler.fragment.AbsBaseFragment;
import com.sunhongxu.a36kr.view.LoginEditText;

/**
 * Created by dllo on 16/9/23.
 * 登录Fragment
 */
public class Login_Fragment extends AbsBaseFragment {
    private LoginEditText userEt;
    private LoginEditText pwdEt;

    public static Login_Fragment newInstance() {
        Login_Fragment fragment = new Login_Fragment();
        return fragment;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {
        userEt = byView(R.id.edit_user);
        pwdEt = byView(R.id.edit_pwd);
    }

    @Override
    protected void initDatas() {

    }
}
