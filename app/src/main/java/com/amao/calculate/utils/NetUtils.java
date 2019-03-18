package com.amao.calculate.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.io.IOException;

/**
 * 网络相关工具类
 */
public class NetUtils {

    private static ConnectivityManager connectivityManager;

    private static ConnectivityManager getConnectivityManager(){
        if(connectivityManager == null){
            Context context = x.app().getApplicationContext();
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        return connectivityManager;
    }

    /**
     * 判断网络是否连接
     */
    public static boolean isConnected() {
        ConnectivityManager manager = getConnectivityManager();
        if(manager == null){
            return false;
        }

        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED;
    }

    /**
     * 判断是否是手机移动数据连接
     */
    public static boolean isMobileDataConnected() {
        ConnectivityManager manager = getConnectivityManager();
        if (manager == null){
            return false;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }


    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifiConnected() {
        ConnectivityManager manager = getConnectivityManager();
        if (manager == null){
            return false;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info != null && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 打开网络设置界面
     */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

    /**
     * 网络是否可用
     * <p>进行ping的操作</p>
     *
     * @param context 上下文
     * @return true：网络畅通，false：网络不可用
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean isConnected = false;
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                android.net.Network[] networks = connectivity.getAllNetworks();
                for (android.net.Network network : networks) {
                    NetworkInfo info = connectivity.getNetworkInfo(network);
                    if (info != null) {
                        int type = info.getType();
                        //VLog.d("Network info:" + info.toString());
                        switch (type) {
                            case ConnectivityManager.TYPE_MOBILE:
                            case ConnectivityManager.TYPE_WIFI:
                                boolean connected = info.isConnected();
                                boolean available = info.isAvailable();
                                //VLog.d("connected:" + connected + ",available:" + available);
                                isConnected = connected && available;
                                if (isConnected) {
                                    isConnected = ping();
                                }
                                break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return isConnected;
    }

    public static boolean ping() {
        String host = "www.baidu.com";
        return ping(host);
    }

    private static boolean ping(String host) {
        String result = null;
        try {
            Process p = Runtime.getRuntime().exec("ping -c 2 -i 0.2 -W 1 " + host);

            // PING的状态
            int status = p.waitFor();
            if (status == 0) {
                result = "Successful.";
                return true;
            } else {
                result = "Failed Cannot Reach The IP Address.";
            }
        } catch (IOException e) {
            result = "Failed IOException.";
        } catch (InterruptedException e) {
            result = "Failed InterruptedException.";
        } finally {
           LogUtil.e("Ping Result : " + result);
        }
        return false;
    }
}
