package com.amao.calculate.core;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.amao.calculate.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.x;



/**
 * 继续补充..
 * 【适用性】1，常规弹框 : 默认点击外部会dismiss (可修改); 弹框宽度会占手机屏幕宽度的78%
 *          2，仿IOS底部弹出
 */

public abstract class BaseDialog extends DialogFragment {

    private CoreApi coreApi;

    protected String tag;

    protected FragmentManager manager;

    private boolean isCanTouchOutside = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = initStyle();
        if (style == Gravity.BOTTOM) {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.style_dialog_bottom);
        } else {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.style_dialog);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(coreApi != null){
            coreApi.unregisterHandler(this);
        }
    }

    /***
     * 在onCreate()中注册
     */
    protected void registerHandler() {
        if(coreApi == null){
            coreApi = new CoreApiImpl();
            coreApi.registerHandler(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == MotionEvent.ACTION_UP) {
                    if(isCanTouchOutside){
                        dismiss();
                    }
                    return true;
                }
                return false;
            }

        });

        View view = inflater.inflate(getContentViewId(), container, false);
        x.view().inject(this, view);

        initViewData();
        return view;
    }


    public int initStyle(){
        return Gravity.CENTER;
    }

    public void setContext(String tag, FragmentManager manager){
        this.tag = tag;
        this.manager = manager;
    }

    public void show(){
        if(manager != null && tag != null){
            show(manager, tag);
        }
    }

    public String getHashTag(){
        return String.valueOf(hashCode());
    }

    @Override
    public void dismiss() {
        dismissAllowingStateLoss();
        if(coreApi != null){
            coreApi.unregisterHandler(this);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        if(window == null){
            return;
        }

        int style = initStyle();
        if(style == Gravity.BOTTOM){
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            int width = (int) (DensityUtil.getScreenWidth() * 0.78);
            window.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private int getContentViewId() {
        ContentView contentView = getClass().getAnnotation(ContentView.class);
        if (contentView == null) {
            throw new RuntimeException("Dialog not found annotation ContentView.");
        }
        return contentView.value();
    }

    protected abstract void initViewData();

    public void setCanceledOnTouchOutside(boolean isCanTouchOutside) {
        this.isCanTouchOutside = isCanTouchOutside;
        setCancelable(isCanTouchOutside);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleMainMsg(DataCourier courier) {
        coreApi.handleMessage(this, SwitchCase.MAIN, courier);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void handleBackgroundMsg(DataCourier courier) {
        coreApi.handleMessage(this, SwitchCase.BACKGROUND, courier);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void handleAsyncMsg(DataCourier courier) {
        coreApi.handleMessage(this, SwitchCase.ASYNC, courier);
    }


}
