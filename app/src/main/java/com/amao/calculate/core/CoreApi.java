package com.amao.calculate.core;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.amao.calculate.dialog.LoadingDialog;


/**
 * 基础接口类
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 17-12-4 下午6:09
 */
public interface CoreApi {

    /**
     * 创建Dialog,并缓存
     *
     * @param cls    Dialog的类型
     * @param params Dialog构造函数的参数值
     * @return 实例化后的参数值
     */
    <T extends BaseDialog> T buildDialog(FragmentManager manager, Class<T> cls, Object... params);

    /**
     * 创建Dialog,并缓存
     *
     * @param cls    Dialog的类型
     * @param params Dialog构造函数的参数值
     * @return 实例化后的参数值
     */
    <T extends BaseDialog> T buildDialog(String tag, FragmentManager manager, Class<T> cls, Object... params);

    /***
     * 隐藏Dialog
     * @param tag 需要隐藏的Dialog的tag
     */
    void hideDialog(String tag);

    <T extends BaseDialog> T getDialog(String tag);

    /***
     * 隐藏所有Dialog
     */
    void hideDialog();

    /***
     * 清空注销所有Dialog
     */
    void clearDialog();

    /**
     * 显示Loading
     */
    LoadingDialog showLoading(FragmentManager manager);

    /**
     * 隐藏Loading
     */
    void hideLoading();

    /**
     * 跳转Activity
     *
     * @param context     上下文
     * @param clsActivity 目标Activity
     * @param bundle      传递的数据
     * @param flags       intent的flags, 默认FLAG_ACTIVITY_SINGLE_TOP
     */
    void toActivity(Activity context, Class<?> clsActivity, Bundle bundle, int... flags);

    /***
     * 跳转Activity
     * @param context 上下文
     * @param clsActivity 目标Activity
     * @param bundle 传递的数据
     * @param reqCode 请求操作码
     * @param flags intent的flags, 默认FLAG_ACTIVITY_SINGLE_TOP
     */
    void toActivityForResult(Activity context, Class<?> clsActivity, Bundle bundle, int reqCode, int... flags);

    /***
     * 跳转Activity
     * @param context 上下文
     * @param clsActivity 目标Activity
     * @param bundle 传递的数据
     * @param reqCode 请求操作码
     * @param flags intent的flags, 默认FLAG_ACTIVITY_SINGLE_TOP
     */
    void toActivityForResult(Fragment context, Class<?> clsActivity, Bundle bundle, int reqCode, int... flags);

    /***
     * 注册事件处理机制
     * @param obj 事件处理者
     */
    void registerHandler(Object obj);

    /***
     * 反注册注册事件处理机制
     * @param obj 事件处理者
     */
    void unregisterHandler(Object obj);

    /**
     * 消息时间处理分发
     *
     * @param obj        事件处理者
     * @param threadMode 线程模式
     * @param courier    数据
     */
    void handleMessage(Object obj, int threadMode, DataCourier courier);

}
