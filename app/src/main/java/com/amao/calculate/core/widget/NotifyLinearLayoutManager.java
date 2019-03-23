package com.amao.calculate.core.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import org.xutils.common.util.LogUtil;

/**
 * 重写 onLayoutChildren方法，处理recycle刷新带来的问题
 * <br/>
 * <li>Author 李东
 * <li>Email lidong@vargo.com.cn
 * <li>Date 2018/4/28 13:12
 */
public class NotifyLinearLayoutManager extends LinearLayoutManager {

    public NotifyLinearLayoutManager(Context context) {
        super(context);
    }

    public NotifyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NotifyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try{
            super.onLayoutChildren(recycler, state);
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            LogUtil.e(e);
        }
    }
}
