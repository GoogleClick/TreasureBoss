package com.ricer.treasureboss.ui;

import android.content.Intent;

import com.ricer.treasureboss.R;
import com.ricer.treasureboss.common.BaseActivity;
import com.ricer.treasureboss.common.UIUtils;

/**
 * Created by Ricer on 2018/1/7.
 */

public class WelcomeActivity extends BaseActivity {
    @Override
    public int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            }
        },500);

    }

    @Override
    public void initData() {
//        gotoActivity(MainActivity.class, null);
    }

}
