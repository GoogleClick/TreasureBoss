package com.ricer.treasureboss.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.ricer.treasureboss.bean.User;

import butterknife.ButterKnife;

/**
 * Created by Ricer on 2018/1/6.
 */

public abstract class BaseActivity extends FragmentActivity {

    public static final String USER_INFO = "user_info";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        ActivityManager.getInstance().add(this);

        initView();
        initData();
    }

    public abstract int getLayout();

    public abstract void initView();

    public abstract void initData();

    public void gotoActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null && bundle.size() != 0) {
            intent.putExtra("data", bundle);
            startActivity(intent);
        }
    }

    public void removeCurrentActivity() {
        ActivityManager.getInstance().removeCurrent();
    }

    public void removeAllActivity() {
        ActivityManager.getInstance().removeAll();
    }

    //保存所有用户信息
    public void saveUser(User user) {
        SharedPreferences sharedPreferences = this.getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", user.getName());
        editor.putString("imageurl", user.getImageurl());
        editor.putBoolean("iscredit", user.isCredit());
        editor.putString("phone", user.getPhone());
        editor.commit();
    }

    public User ReadtUser() {
        SharedPreferences sp = this.getSharedPreferences(USER_INFO, MODE_PRIVATE);
        User user = new User();
        user.setName(sp.getString("name", ""));
        user.setImageurl(sp.getString("imageurl", ""));
        user.setPhone(sp.getString("phone", ""));
        user.setCredit(sp.getBoolean("iscredit", false));
        return user;
    }
}
