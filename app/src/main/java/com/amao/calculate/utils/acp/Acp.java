package com.amao.calculate.utils.acp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

/**
 * 运行时权限检查类
 * https://github.com/mylhyl/AndroidAcp
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 17-12-15 下午12:26
 */
public class Acp {

    private static Acp mInstance;
    private AcpManager mAcpManager;

    public static Acp getInstance(Context context) {
        if (mInstance == null)
            synchronized (Acp.class) {
                if (mInstance == null) {
                    mInstance = new Acp(context);
                }
            }
        return mInstance;
    }

    private Acp(Context context) {
        mAcpManager = new AcpManager(context.getApplicationContext());
    }

    /**
     * 开始请求
     *
     * @param options
     * @param acpListener
     */
    public void request(AcpOptions options, AcpListener acpListener) {
        if (options == null) throw new NullPointerException("AcpOptions is null...");
        if (acpListener == null) throw new NullPointerException("AcpListener is null...");
        mAcpManager.request(options, acpListener);
    }

    AcpManager getAcpManager() {
        return mAcpManager;
    }


    public static boolean checkPermission(String... permissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        Context context = x.app();
        int len = (permissions == null ? 0 : permissions.length);
        for (int i = 0; i < len; i++) {
            String permission = permissions[i];
            if (Manifest.permission.SYSTEM_ALERT_WINDOW.equals(permission)) {
                if (!Settings.canDrawOverlays(context)) {
                    return false;
                }
            } else if (Manifest.permission.WRITE_SETTINGS.equals(permission)) {
                if (!Settings.System.canWrite(context)) {
                    return false;
                }
            } else if (checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private static int checkSelfPermission(Context context, String permission) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int targetSdkVersion = info.applicationInfo.targetSdkVersion;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (targetSdkVersion >= Build.VERSION_CODES.M) {
                    return ContextCompat.checkSelfPermission(context, permission);
                } else {
                    return PermissionChecker.checkSelfPermission(context, permission);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e);
        }
        return ContextCompat.checkSelfPermission(context, permission);
    }

    /***
     * 请求权限
     */
    public static void requestPermission(Activity activity, AcpListener listener, String... permissions){
        /*以下为自定义提示语、按钮文字
        .setDeniedMessage()
        .setDeniedCloseBtn()
        .setDeniedSettingBtn()
        .setRationalMessage()
        .setRationalBtn()*/
        AcpOptions.Builder builder = new AcpOptions.Builder();
        builder.setPermissions(permissions);
        Acp.getInstance(activity).request(builder.build(), listener);
    }



}
