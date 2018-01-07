package com.ricer.treasureboss.ui;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ricer.treasureboss.R;
import com.ricer.treasureboss.common.BaseActivity;
import com.ricer.treasureboss.common.UIUtils;
import com.ricer.treasureboss.ui.fragment.HomeFragment;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Ricer on 2018/1/7.
 */

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_fragment)
    FrameLayout mainFragment;
    @Bind(R.id.main_bottom)
    LinearLayout mainBottom;
    @Bind(R.id.radio_home)
    LinearLayout radioHome;
    @Bind(R.id.home_image)
    ImageView homeImage;
    @Bind(R.id.home_tv)
    TextView homeTv;
    @Bind(R.id.radio_invest)
    LinearLayout radioInvest;
    @Bind(R.id.invest_image)
    ImageView investImage;
    @Bind(R.id.invest_tv)
    TextView investTv;
    @Bind(R.id.radio_me)
    LinearLayout radioMe;
    @Bind(R.id.me_image)
    ImageView meImage;
    @Bind(R.id.me_tv)
    TextView meTv;
    @Bind(R.id.radio_more)
    LinearLayout radioMore;
    @Bind(R.id.more_image)
    ImageView moreImage;
    @Bind(R.id.more_tv)
    TextView moreTv;

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.radio_home, R.id.radio_invest, R.id.radio_me, R.id.radio_more})
    public void showTab(View view) {
        switch (view.getId()) {
            case R.id.radio_home:
                setSelect(0);
                break;
            case R.id.radio_invest:
                setSelect(1);
                break;
            case R.id.radio_me:
                setSelect(2);
                break;
            case R.id.radio_more:
                setSelect(3);
                break;
        }
    }

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private InvestFragment investFragment;
    private MeFragment meFragment;
    private MoreFragment moreFragment;

    private void setSelect(int id) {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        //隐藏所有Fragment的显示
        hideFragments();
        //重置ImageView和TextView的显示状态
        resetTab();

        switch (id) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.main_fragment, homeFragment);
                }
                transaction.show(homeFragment);
                homeImage.setImageResource(R.drawable.home);
                homeTv.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                break;
            case 1:
                if (investFragment == null) {
                    investFragment = new InvestFragment();
                    transaction.add(R.id.main_fragment, investFragment);
                }
                transaction.show(investFragment);
                investImage.setImageResource(R.drawable.invest);
                investTv.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                break;
            case 2:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                    transaction.add(R.id.main_fragment, meFragment);
                }
                transaction.show(meFragment);
                meImage.setImageResource(R.drawable.me);
                meTv.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                break;
            case 3:
                if (moreFragment == null) {
                    moreFragment = new MoreFragment();
                    transaction.add(R.id.main_fragment, moreFragment);
                }
                transaction.show(moreFragment);
                moreImage.setImageResource(R.drawable.more);
                moreTv.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                break;
        }
        transaction.commit();
    }

    private void hideFragments() {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (investFragment != null) {
            transaction.hide(investFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
        if (moreFragment != null) {
            transaction.hide(moreFragment);
        }

    }

    private void resetTab() {
        homeImage.setImageResource(R.drawable.home_gray);
        investImage.setImageResource(R.drawable.invest_gray);
        meImage.setImageResource(R.drawable.me_gray);
        moreImage.setImageResource(R.drawable.more_gray);

        homeTv.setTextColor(UIUtils.getColor(R.color.colorTextHint));
        investTv.setTextColor(UIUtils.getColor(R.color.colorTextHint));
        meTv.setTextColor(UIUtils.getColor(R.color.colorTextHint));
        moreTv.setTextColor(UIUtils.getColor(R.color.colorTextHint));
    }


    @Override
    public void initData() {
        setSelect(0);
    }

    //重写onKeyUp()，实现连续两次点击方可退出当前应用
    private boolean flag = true;
    private static final int WHAT_RESET_BACK = 1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.e("TAG", "handleMessage");
            switch (msg.what) {
                case WHAT_RESET_BACK:
                    flag = true;//复原
                    break;
            }
        }
    };

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && flag) {
            Toast.makeText(MainActivity.this, "再点击一次，退出当前应用", Toast.LENGTH_SHORT).show();
            flag = false;
            //发送延迟消息
            handler.sendEmptyMessageDelayed(WHAT_RESET_BACK, 2000);
            return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    //为了避免出现内存的泄漏，需要在onDestroy()中，移除所有未被执行的消息
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //方式一：
        //    handler.removeMessages(WHAT_RESET_BACK);//移除指定id的所有的消息
        //方式二：移除所有的未被执行的消息
        handler.removeCallbacksAndMessages(null);
    }
}
