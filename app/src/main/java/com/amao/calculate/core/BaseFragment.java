package com.amao.calculate.core;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amao.calculate.utils.AppUtils;
import com.amao.calculate.utils.acp.Acp;
import com.amao.calculate.utils.acp.AcpListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;


/**
 * 基类Fragment,处理Fragment的公共业务
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 17-12-4 下午8:03
 */
public abstract class BaseFragment extends Fragment {

    protected FragmentActivity activity;

    protected CoreApi coreApi;

    private BaseFragment baseFragment;

    private View root_view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.coreApi = new CoreApiImpl();
    }

    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        root_view = x.view().inject(this, inflater, container);
        this.activity = getActivity();
        return root_view;
    }

    public void showView() {
        if (root_view != null && root_view.getVisibility() != View.VISIBLE) {
            root_view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        onCreateView(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        onShow(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        onHide(true);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            coreApi.hideDialog(); // 界面隐藏时,关闭所有Dialog
            onHide(false);
        } else {
            onShow(false);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    protected void onCreateView(View view, Bundle savedInstanceState) {
    }

    protected void onHide(boolean isOnPause) {
    }

    protected void onShow(boolean isOnResume) {
    }

    protected final <DLG extends BaseDialog> DLG buildDialog(String tag, Class<DLG> cls, Object... params) {
        return coreApi.buildDialog(tag, getFragmentManager(), cls, params);
    }

    public final <DLG extends BaseDialog> DLG buildDialog(Class<DLG> cls, Object... params) {
        return coreApi.buildDialog(getFragmentManager(), cls, params);
    }

    protected final void hideDialog(String tag) {
        coreApi.hideDialog(tag);
    }

    protected BaseDialog getDialog(String tag) {
        return coreApi.getDialog(tag);
    }

    protected void showLoading() {
        coreApi.showLoading(getFragmentManager());
    }

    protected void hideLoading() {
        coreApi.hideLoading();
    }

    protected void registerHandler() {
        coreApi.registerHandler(this);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getAct() {
        return (T) activity;
    }

    protected <T> T getSerializable(String key, Intent intent) {
        return AppUtils.getSerializable(key, intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        coreApi.clearDialog();
        coreApi.unregisterHandler(this);
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

    protected void toActivity(Class<?> clsActivity, int... flags) {
        coreApi.toActivity(activity, clsActivity, null, flags);
    }

    protected void toActivity(Class<?> clsActivity, Bundle bundle, int... flags) {
        coreApi.toActivity(activity, clsActivity, bundle, flags);
    }

    protected void toActivityForResult(Class<?> clsActivity, int requestCode, int... flags) {
        coreApi.toActivityForResult(this, clsActivity, null, requestCode, flags);
    }

    protected void toActivityForResult(Class<?> clsActivity, Bundle bundle, int requestCode, int... flags) {
        coreApi.toActivityForResult(this, clsActivity, bundle, requestCode, flags);
    }

    protected LoaderManager getSupportLoaderManager() {
        return activity.getSupportLoaderManager();
    }

    protected <D> Loader<D> initLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<D> callback) {
        return getSupportLoaderManager().initLoader(id, args, callback);
    }

    protected <D> Loader<D> restartLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<D> callback) {
        return getSupportLoaderManager().restartLoader(id, args, callback);
    }

    protected void requestPermission(AcpListener listener, String... permissions) {
        Acp.requestPermission(activity, listener, permissions);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof BaseFragment) {
            ((BaseFragment) parentFragment).startActivityForResultFromChildFragment(intent, requestCode, this);
        } else {
            baseFragment = null;
            super.startActivityForResult(intent, requestCode);
        }
    }

    private void startActivityForResultFromChildFragment(Intent intent, int requestCode, BaseFragment childFragment) {
        baseFragment = childFragment;

        Fragment parentFragment = getParentFragment();
        if (parentFragment != null && parentFragment instanceof BaseFragment) {
            ((BaseFragment) parentFragment).startActivityForResultFromChildFragment(intent, requestCode, this);
        } else {
            super.startActivityForResult(intent, requestCode);
        }
    }

    @Override
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (baseFragment != null) {
            baseFragment.onActivityResult(requestCode, resultCode, data);
            baseFragment = null;
        } else {
            onActivityResultNestedCompat(requestCode, resultCode, data);
        }
    }

    public void onActivityResultNestedCompat(int requestCode, int resultCode, Intent data) {
    }
}
