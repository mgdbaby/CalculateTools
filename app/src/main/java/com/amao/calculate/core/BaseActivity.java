package com.amao.calculate.core;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.amao.calculate.utils.AppUtils;
import com.amao.calculate.utils.acp.Acp;
import com.amao.calculate.utils.acp.AcpListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.xutils.x;


/**
 * 基类Activity,处理Activity的公共业务
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 17-12-4 下午6:51
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected CoreApi coreApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        this.coreApi = new CoreApiImpl();
    }

    /***
     * 在onCreate()中注册
     */
    protected void registerHandler() {
        coreApi.registerHandler(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        coreApi.unregisterHandler(this);
        coreApi.clearDialog();
    }

    @Override
    public Resources getResources() {//禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    public void goBack(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    protected final <T extends BaseDialog> T buildDialog(String tag, Class<T> cls, Object... params) {
        return coreApi.buildDialog(tag, getSupportFragmentManager(), cls, params);
    }

    public final <T extends BaseDialog> T buildDialog(Class<T> cls, Object... params) {
        return coreApi.buildDialog(getSupportFragmentManager(), cls, params);
    }

    protected final void hideDialog(String tag) {
        coreApi.hideDialog(tag);
    }

    protected void showLoading() {
        coreApi.showLoading(getSupportFragmentManager());
    }

    protected void hideLoading() {
        coreApi.hideLoading();
    }

    private Bundle bundle;

    protected Bundle loadBundle() {
        Intent intent = getIntent();
        if (intent != null) {
            bundle = intent.getExtras();
        }
        return bundle;
    }

    protected <T> T getSerializable(String key, Intent intent) {
        return AppUtils.getSerializable(key, intent);
    }

    @SuppressWarnings("unchecked")
    protected <T> T getSerializable(String key) {
        if (bundle == null) {
            loadBundle();
        }
        return (T) (bundle == null ? null : bundle.getSerializable(key));
    }

    @SuppressWarnings("unchecked")
    protected <T> T getParam(String key) {
        if (bundle == null) {
            loadBundle();
        }
        return (T) (bundle == null ? null : bundle.get(key));
    }

    protected <T> T getParam(String key, T defValue) {
        T t = getParam(key);
        return t == null ? defValue : t;
    }

    public void toActivity(Class<?> clsActivity, int... flags) {
        coreApi.toActivity(this, clsActivity, null, flags);
    }

    public void toActivity(Class<?> clsActivity, Bundle bundle, int... flags) {
        coreApi.toActivity(this, clsActivity, bundle, flags);
    }

    protected <D> Loader<D> initLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<D> callback) {
        return getSupportLoaderManager().initLoader(id, args, callback);
    }

    protected <D> Loader<D> restartLoader(int id, Bundle args, LoaderManager.LoaderCallbacks<D> callback) {
        return getSupportLoaderManager().restartLoader(id, args, callback);
    }

    public void toActivityForResult(Class<?> clsActivity, int requestCode, int... flags) {
        coreApi.toActivityForResult(this, clsActivity, null, requestCode, flags);
    }

    public void toActivityForResult(Class<?> clsActivity, Bundle bundle, int requestCode, int... flags) {
        coreApi.toActivityForResult(this, clsActivity, bundle, requestCode, flags);
    }

    protected void requestPermission(AcpListener listener, String... permissions) {
        Acp.requestPermission(this, listener, permissions);
    }

}
