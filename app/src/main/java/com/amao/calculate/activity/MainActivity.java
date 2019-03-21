package com.amao.calculate.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amao.calculate.R;
import com.amao.calculate.core.BaseActivity;
import com.amao.calculate.core.BaseFragment;
import com.amao.calculate.fragment.PageOneFragment;
import com.amao.calculate.fragment.PageThreeFragment;
import com.amao.calculate.fragment.PageTwoFragment;
import com.amao.calculate.fragment.UserFragment;
import com.amao.calculate.utils.StringUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 主界面
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/18 18:47
 */
@ContentView(R.layout.act_main)
public class MainActivity extends BaseActivity {

    private static final int PAGE_COUNT = 4;
    private final int PAGE_ONE = 0;
    private final int PAGE_TWO = 1;
    private final int PAGE_THREE = 2;
    private final int PAGE_USER = 3;

    @ViewInject(R.id.text_msg)
    private TextView btn_msg;

    @ViewInject(R.id.text_group_msg)
    private TextView text_group_msg;

    @ViewInject(R.id.text_circle)
    private TextView text_circle;

    @ViewInject(R.id.text_my)
    private TextView text_my;

    private int currPageIdx = -1;        // 当前选中项,-1表示从未加载过


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBottomView();
        switchPage(PAGE_ONE);  // 默认选择第一页
        registerHandler();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /***
     * 初始化加载底部菜单栏宽度,以及提示的位置
     */
    private void initBottomView() {
        int width = DensityUtil.getScreenWidth() / PAGE_COUNT;

        setTextViewWidth(btn_msg, width);
        setTextViewWidth(text_group_msg, width);
        setTextViewWidth(text_circle, width);
        setTextViewWidth(text_my, width);
    }

    private void setTextViewWidth(TextView textView, int width) {
        if (null != textView) {
            ViewGroup.LayoutParams layoutParams = textView.getLayoutParams();
            layoutParams.width = width;
            textView.setLayoutParams(layoutParams);
        }
    }

    private String getFragmentTag(int idx) {
        return "com.calculate.main.tag_" + idx;
    }

    private Fragment getFragmentByIdx(int idx) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.findFragmentByTag(getFragmentTag(idx));
    }

    private void switchPage(int idx) {
        if (idx == currPageIdx) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (currPageIdx >= 0) {
            BaseFragment oldFra = (BaseFragment) getFragmentByIdx(currPageIdx);
            if (oldFra != null) {
                transaction.hide(oldFra);
            }
        }

        String tag = getFragmentTag(idx);
        BaseFragment newFra = (BaseFragment) fragmentManager.findFragmentByTag(tag);
        if (newFra == null) {
            newFra = newFragment(idx);
            transaction.add(R.id.frame_content, newFra, tag);
        }


        this.currPageIdx = idx;

        transaction.show(newFra);
        transaction.commit();
    }

    // 创建Fragment实例
    private BaseFragment newFragment(int idx) {
        if (idx < PAGE_ONE || idx >= PAGE_COUNT) {
            throw new RuntimeException("Page index out of range.");
        }
        if (idx == PAGE_TWO) return new PageTwoFragment();
        if (idx == PAGE_THREE) return new PageThreeFragment();
        if (idx == PAGE_USER) return new UserFragment();
        return new PageOneFragment();
    }

    // 底部按钮点击事件
    @Event(value = {R.id.text_msg, R.id.text_group_msg, R.id.text_circle, R.id.text_my})
    private void onTabClick(View view) {
        int idx = StringUtils.toInt(String.valueOf(view.getTag()));
        if (idx >= PAGE_COUNT || idx < PAGE_ONE || idx == currPageIdx) {
            return;
        }

        if (currPageIdx == PAGE_TWO) {
            text_group_msg.setEnabled(true);
        } else if (currPageIdx == PAGE_THREE) {
            text_circle.setEnabled(true);
        } else if (currPageIdx == PAGE_USER) {
            text_my.setEnabled(true);
        } else {
            btn_msg.setEnabled(true);
        }
        view.setEnabled(false);
        switchPage(idx);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Fragment fragment = getFragmentByIdx(currPageIdx);
            if (fragment == null || !((BaseFragment) fragment).onKeyDown(keyCode, event)) {
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
