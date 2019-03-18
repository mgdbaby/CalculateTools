package com.amao.calculate.width.core;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 应用Activity生命周期的回调
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 18/6/25 23:06
 */
public class ActivityLifeManager implements Application.ActivityLifecycleCallbacks {

    private static AtomicInteger actNum = new AtomicInteger(0);
    private static WeakReference<Activity> weakReference;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (weakReference != null) {
            weakReference.clear();
        }
        weakReference = new WeakReference<>(activity);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        actNum.incrementAndGet();
    }

    @Override
    public void onActivityPaused(Activity activity) {
        actNum.decrementAndGet();
        if (actNum.get() < 0) {
            actNum.set(0);
        }
    }


    @Override
    public void onActivityStopped(Activity activity) {
        if (actNum.get() <= 0) {
//            CornerUtils.updateBadge();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public static boolean isAppBackground() {
        return actNum.get() <= 0;
    }

    public static int getActivityCount() {
        return actNum.get();
    }

    public static FragmentActivity getTopActivity() {
        return weakReference == null ? null : (FragmentActivity) weakReference.get();
    }

}
