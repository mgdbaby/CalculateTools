package com.amao.calculate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amao.calculate.R;
import com.amao.calculate.core.BaseFragment;

import org.xutils.view.annotation.ContentView;

/**
 * 用户信息fragment
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/20 10:46
 */
@ContentView(R.layout.fragment_main_two)
public class PageTwoFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerHandler();
    }
}
