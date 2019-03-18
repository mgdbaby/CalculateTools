package com.amao.calculate.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.SparseArray;

import com.amao.calculate.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;


/**
 * 基础类公用部分代码
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 17-12-4 下午4:40
 */
@SuppressWarnings("unchecked")
public class CoreApiImpl implements CoreApi {

    public static final String DLG_TAG_LOADING = "dlg_loading";

    /**
     * 事件方法处理
     **/
    private SparseArray<Method> methodMap;

    /**
     * Dialog缓存
     **/
    private SimpleArrayMap<String, BaseDialog> dialogMap;

    @Override
    public void registerHandler(Object obj) {
        if (obj == null) {
            LogUtil.w("registerHandler object is null.");
            return;
        }
        if (methodMap == null) {
            methodMap = new SparseArray<>();
        } else {
            methodMap.clear();
        }
        ReflexProxy.loadCaseMethod(obj, methodMap);

        EventBus eventBus = EventBus.getDefault();
        if (!eventBus.isRegistered(obj)) {
            eventBus.register(obj);
        } else {
            LogUtil.e("Duplication of registration. class = " + obj.getClass().getName());
        }
    }

    @Override
    public void unregisterHandler(Object obj) {
        if (obj == null) {
            LogUtil.w("registerHandler object is null.");
            return;
        }

        if (methodMap != null) {
            methodMap.clear();
            methodMap = null;
        }

        EventBus eventBus = EventBus.getDefault();
        if (eventBus.isRegistered(obj)) {
            eventBus.unregister(obj);
        }
    }

    @Override
    public void handleMessage(Object obj, int threadMode, DataCourier courier) {
        ReflexProxy.handleMessage(obj, methodMap, threadMode, courier);
    }

    @Override
    public <T extends BaseDialog> T buildDialog(FragmentManager manager, Class<T> cls, Object... params) {
        return buildDialog(null, manager, cls, params);
    }

    @Override
    public <T extends BaseDialog> T buildDialog(String tag, FragmentManager manager, Class<T> cls, Object... params) {
        try {
            if (dialogMap == null) {
                dialogMap = new SimpleArrayMap<>(3);
            }

            T dialog;
            if (params != null && params.length > 0) {
                Class[] paramsCls = new Class[params.length];
                for (int i = 0; i < params.length; i++) {
                    Object obj = params[i];
                    if (obj == null) {
                        throw new RuntimeException("Constructor params not to be null.");
                    }
                    paramsCls[i] = obj.getClass();
                }
                Constructor<T> constructor = cls.getConstructor(paramsCls);
                dialog = constructor.newInstance(params);
            } else {
                Constructor<T> constructor = cls.getConstructor();
                dialog = constructor.newInstance();
            }

            if (TextUtils.isEmpty(tag)) {
                tag = dialog.getHashTag();
            }
            dialog.setContext(tag, manager);
            dialogMap.put(tag, dialog);
            return dialog;
        } catch (Exception e) {
            LogUtil.e(e);
        }
        throw new RuntimeException("Build dialog fail.");
    }

    @Override
    public void hideDialog(String tag) {
        if (dialogMap == null || dialogMap.size() <= 0) {
            return;
        }

        BaseDialog dialog = dialogMap.get(tag);
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void hideDialog() {
        int size = (dialogMap == null ? 0 : dialogMap.size());
        for (int i = 0; i < size; i++) {
            BaseDialog dialog = dialogMap.valueAt(i);
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public <T extends BaseDialog> T getDialog(String tag) {
        if (TextUtils.isEmpty(tag) || dialogMap == null) {
            return null;
        }
        return (T) dialogMap.get(tag);
    }

    @Override
    public void clearDialog() {
        if (dialogMap != null) {
            dialogMap.clear();
            dialogMap = null;
        }
    }

    @Override
    public LoadingDialog showLoading(FragmentManager manager) {
        LoadingDialog dialog = buildDialog(DLG_TAG_LOADING, manager, LoadingDialog.class);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    @Override
    public void hideLoading() {
        hideDialog(DLG_TAG_LOADING);
    }

    @Override
    public void toActivity(Activity context, Class<?> clsActivity, Bundle bundle, int... flags) {
        Intent intent = new Intent(context, clsActivity);

        if (bundle != null) {
            intent.putExtras(bundle);
        }

        if (flags == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        context.startActivity(intent);
    }

    @Override
    public void toActivityForResult(Activity context, Class<?> clsActivity, Bundle bundle, int reqCode, int... flags) {
        Intent intent = new Intent(context, clsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flags == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        context.startActivityForResult(intent, reqCode);
    }

    @Override
    public void toActivityForResult(Fragment context, Class<?> clsActivity, Bundle bundle, int reqCode, int... flags) {
        Intent intent = new Intent(context.getActivity(), clsActivity);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        if (flags == null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        } else {
            for (int flag : flags) {
                intent.addFlags(flag);
            }
        }
        context.startActivityForResult(intent, reqCode);
    }
}
