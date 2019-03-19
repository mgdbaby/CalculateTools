package com.amao.calculate.activity;

import android.os.Bundle;

import com.amao.calculate.R;
import com.amao.calculate.core.BaseActivity;

import org.xutils.view.annotation.ContentView;

/**
 * 主界面
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/18 18:47
 */
@ContentView(R.layout.layout_main)
public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerHandler();


    }
}
