package com.ricer.treasureboss.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.RequestParams;
import com.ricer.treasureboss.ui.LoadingPage;

import butterknife.ButterKnife;

/**
 * Created by Ricer on 2018/1/6.
 */

public abstract class BaseFragment extends Fragment {
    LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        loadingPage = new LoadingPage(getContext()) {
            @Override
            protected void onSuccess(ResultState resultState, View view_success) {
                ButterKnife.bind(BaseFragment.this, view_success);
                initView();
                initData(resultState.getContent());

            }

            @Override
            protected String url() {
                return getUrl();
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            public int layoutId() {
                return getLayoutRes();
            }
        };
        return loadingPage;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show();
    }

    protected abstract RequestParams getParams();

    protected abstract String getUrl();

    protected abstract int getLayoutRes();

    protected abstract void initData(String data);

    protected abstract void initView();

    public void show() {
        loadingPage.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
