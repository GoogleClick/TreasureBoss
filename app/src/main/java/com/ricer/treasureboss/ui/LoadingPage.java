package com.ricer.treasureboss.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.ricer.treasureboss.R;
import com.ricer.treasureboss.common.UIUtils;

/**
 * Created by Ricer on 2018/1/6.
 * 根据场景管理fragment
 */

public abstract class LoadingPage extends FrameLayout {

    //1.定义4种不同的显示状态
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;

    private int state_current = STATE_LOADING;//默认情况下，当前状态为正在加载

    //2.提供4种不同的界面
    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;
    private LayoutParams params;

    private Context mContext;

    public LoadingPage(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (view_loading == null) {
            view_loading = UIUtils.getView(R.layout.page_loading);
            addView(view_loading, params);
        }
        if (view_error == null) {
            view_error = UIUtils.getView(R.layout.page_error);
            addView(view_error, params);
        }
        if (view_empty == null) {
            view_empty = UIUtils.getView(R.layout.page_empty);
            addView(view_empty, params);
        }
        showSafePage();
    }

    //保证如下的操作在主线程中执行的：更新界面
    private void showSafePage() {
        UIUtils.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    private void showPage() {
        //根据当前state_current的值，决定显示哪个view
        view_loading.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        view_error.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        view_empty.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        if (view_success == null) {
            view_success=View.inflate(mContext, layoutId(), null);
            addView(view_success);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);
    }

    //设置联网请求数据
    public void show() {
        if (TextUtils.isEmpty(url())) {
            resultState = ResultState.SUCCESS;
            resultState.setContent("");
            loadImage();
            return;
        }
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //使用第三方网络异步加载 async-http-master
                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                asyncHttpClient.get(url(), params(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        if (TextUtils.isEmpty(content)) {
                            resultState = ResultState.EMPTY;
                            resultState.setContent("");
                        } else {
                            resultState = ResultState.SUCCESS;
                            resultState.setContent(content);
                        }
                        loadImage();
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        resultState = ResultState.ERROR;
                        resultState.setContent(content);
                        loadImage();
                    }
                });
            }
        }, 2000);
    }


    //根据修改后的resultState修改显示的视图
    private void loadImage() {
        switch (resultState) {
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;
            case ERROR:
                state_current = STATE_ERROR;
                break;
            case EMPTY:
                state_current = STATE_EMPTY;
                break;
        }
        showSafePage();
        if (state_current == STATE_SUCCESS) {
            onSuccess(resultState,view_success);
        }

    }

    protected abstract void onSuccess(ResultState resultState, View view_success);

    //设置URL地址
    protected abstract String url();

    //设置请求参数
    protected abstract RequestParams params();

    public abstract int layoutId();

    private ResultState resultState;

    //提供枚举类，封装联网以后的状态值和数据
    public enum ResultState {

        ERROR(2), EMPTY(3), SUCCESS(4);

        int state;

        ResultState(int state) {
            this.state = state;
        }

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
