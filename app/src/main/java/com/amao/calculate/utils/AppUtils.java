package com.amao.calculate.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.os.Process;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.amao.calculate.width.core.ActivityLifeManager;

import org.xutils.common.util.IOUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


public class AppUtils {

    private static long oldVibrateTime;
    private static final String TAG = "Vtransfer";

    /**
     * 应用是否在后台
     */
    public static boolean isAppBackground() {
        return ActivityLifeManager.isAppBackground();
    }

    /**
     * 手机震动
     * <p>
     * milliseconds ：震动的时长，单位是毫秒
     */
    public static void vibrate(long milliseconds) {
        Context context = x.app().getApplicationContext();
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        if (vibrator == null) {
            LogUtil.w("Vibrator service is null.");
            return;
        }

        long curTime = System.currentTimeMillis();
        if (curTime - oldVibrateTime <= milliseconds) {
            oldVibrateTime = curTime;
            return;
        }
        vibrator.vibrate(milliseconds);
        oldVibrateTime = curTime;
    }

    public static boolean isMainProcess() {
        Context context = x.app().getApplicationContext();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == Process.myPid()) {
                    String pkgName = context.getPackageName();
                    return pkgName.equals(appProcess.processName);
                }
            }
        }
        return false;
    }

    public static String getString(int resId, Object... args) {
        return x.app().getString(resId, args);
    }

    public static int getColor(int resId) {
        return ContextCompat.getColor(x.app(), resId);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getSerializable(String key, Intent data) {
        if (key == null || data == null) {
            return null;
        }
        return (T) data.getSerializableExtra(key);
    }

    public static ColorStateList getColorStateList(int resId) {
        return ContextCompat.getColorStateList(x.app(), resId);
    }

    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(x.app(), resId);
    }

    public static int getDimens(int resId) {
        return (int) x.app().getResources().getDimension(resId);
    }

    @SuppressLint("HardwareIds")
    public static String getImei() {
        Context context = x.app().getApplicationContext();

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }

        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager == null) {
            return "";
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return manager.getImei();
        } else {
            return manager.getDeviceId();
        }
    }

    /**
     * Open  keyboard.
     *
     * @param context the context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * Close keyboard
     *
     * @param context the context
     */
    public static void closeSoftInput(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && view != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void closeSoftInput(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 是否关闭背景音乐 closeBGM ：true 是
     */
    public static boolean muteAudioFocus(boolean closeBGM) {
        boolean bool;
        AudioManager am = (AudioManager) x.app().getSystemService(Context.AUDIO_SERVICE);
        if (closeBGM) {
            int result = am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        } else {
            int result = am.abandonAudioFocus(null);
            bool = result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
        }
        LogUtil.d("pauseMusic closeBGM = " + closeBGM + " result=" + bool);
        return bool;
    }

    public static void await(CountDownLatch latch, long time) {
        try {
            latch.await(time, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LogUtil.e(e);
        }
    }

    public static void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            LogUtil.e(e);
        }
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    /**
     * 拍照图片裁剪
     **/
    public static Intent getPhotoIntent(Uri cameraUri) {
        if (cameraUri == null) {
            throw new RuntimeException("参数不能为空");
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        return intent;
    }

    /**
     * 设置TextView的DrawableRight
     */
    public static void textDrawableRight(TextView textView, Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, null, drawable, null);
        } else {
            textView.setCompoundDrawables(null, null, null, null);
        }
    }

    public static void textDrawableRight(TextView textView, int resId) {
        Drawable drawable = ResourcesCompat.getDrawable(x.app().getResources(), resId, null);
        textDrawableRight(textView, drawable);
    }

    /**
     * 设置TextView的DrawableLeft
     */
    public static void textDrawableLeft(TextView textView, Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
        }
    }

    public static void textDrawableLeft(TextView textView, int resId) {
        Drawable drawable = ResourcesCompat.getDrawable(x.app().getResources(), resId, null);
        textDrawableLeft(textView, drawable);
    }

    public static void buttonDrawableLeft(Button button, int resId) {
        Drawable drawable = ResourcesCompat.getDrawable(x.app().getResources(), resId, null);
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            button.setCompoundDrawables(drawable, null, null, null);
        }
    }

    public static void textDrawableTop(TextView textView, Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(null, drawable, null, null);
        }
    }

    public static void textDrawableTop(TextView textView, int resId) {
        Drawable drawable = ResourcesCompat.getDrawable(x.app().getResources(), resId, null);
        textDrawableTop(textView, drawable);
    }

    /**
     * 判断指定包名的APK是否安装
     */
    public static boolean isAppInstalled(String packageName) {
        final PackageManager packageManager = x.app().getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        if (packageInfoList != null) {
            for (int i = 0; i < packageInfoList.size(); i++) {
                String pn = packageInfoList.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * 获取文件Uri的文件路径
     */
    public static String getUriFilePath(Uri uri) {
        if (null == uri) return null;
        String scheme = uri.getScheme();
        String data = null;
        if (scheme == null || ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = null;
            try {
                cursor = x.app().getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    data = cursor.getString(0);
                }
            } catch (Exception e) {
                LogUtil.e(e);
            } finally {
                IOUtil.closeQuietly(cursor);
            }
        }
        return data;
    }

    /**
     * 弹出是否忽略电池优化
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void checkIfAppInWhite(Activity context) {
        try {
            Intent intent = new Intent();
            String packageName = context.getBaseContext().getPackageName();
            PowerManager pm = (PowerManager) context.getBaseContext().getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                context.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断某个Activity 界面是否在前台
     *
     * @param className 某个界面名称
     */
    public static boolean actIsForeground(String className) {
        if (TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) x.app().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是主空间
     *
     * @return true 主空间
     */
    public static boolean isMainUserOwner() {
        try {
            Method getUserHandle = android.os.UserManager.class.getMethod("getUserHandle");
            int userHandle = (Integer) getUserHandle.invoke(x.app().getSystemService(Context.USER_SERVICE));
            return userHandle == 0;
        } catch (Exception ex) {
            LogUtil.e(ex);
            return false;
        }
    }

    /**
     * 获取空间编号
     */
    public static int getSpaceId() {
        try {
            Method getUserHandle = android.os.UserManager.class.getMethod("getUserHandle");
            return (Integer) getUserHandle.invoke(x.app().getSystemService(Context.USER_SERVICE));
        } catch (Exception ex) {
            LogUtil.e(ex);
        }
        return 0;
    }

    /**
     * 获取当前版本
     */
    public static String getAppVersionName() {
        String versionName = "";
        try {
            PackageManager pm = x.app().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(x.app().getPackageName(), 0);
            versionName = pi.versionName;
//            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtil.e("VersionInfo_Exception is = " + e.getMessage());
        }
        return versionName;
    }

    private static int STATUS_BAR_HEIGHT;

    public static int getStatusBarHeight() {
        if (STATUS_BAR_HEIGHT <= 0) {
            int resourceId = x.app().getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                STATUS_BAR_HEIGHT = x.app().getResources().getDimensionPixelSize(resourceId);
            }
        }
        return STATUS_BAR_HEIGHT;
    }

    /***
     * WifiP2p绿色通道
     */
    public static void setWifiP2pEnable(boolean enable) {
        LogUtil.d("setWifiP2pEnable : " + enable);
        try {
            ContentResolver resolver = x.app().getContentResolver();
            Settings.System.putInt(resolver, "vargo_wifi_p2p", enable ? 1 : 0);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    /**
     * 获取本机WifiP2p的名称,地址
     */
    public static String getWifiP2pAddress() {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByName("p2p0"); // wlan0 wifi地址
            if (networkInterface == null) {
                networkInterface = NetworkInterface.getByName("P2P0");
            }

            byte[] macBytes = networkInterface.getHardwareAddress();
            int len = (macBytes == null ? 0 : macBytes.length);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < len; i++) {
                builder.append(String.format(":%02x", macBytes[i]));
            }
            if (builder.length() > 0) {
                return builder.deleteCharAt(0).toString();
            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return null;
    }

    private static PowerManager powerManager;
    private static PowerManager.WakeLock wakeLock;

    /**
     * 是否亮屏
     */
    public static boolean isScreenOn() {
        PowerManager powerManager = (PowerManager) x.app().getSystemService(Context.POWER_SERVICE);
        return powerManager != null && powerManager.isInteractive();
    }

    /**
     * 亮屏 + 时间
     */
    @SuppressLint("InvalidWakeLockTag")
    public static void setScreenOff(long time) {
        if (isScreenOn()) {
            return;
        }

        if (powerManager == null) {
            powerManager = (PowerManager) x.app().getSystemService(Context.POWER_SERVICE);
            return;
        }

        wakeLock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, TAG);
        wakeLock.setReferenceCounted(false);
        wakeLock.acquire(time);
    }


    /***
     * 判断App是否安装
     */
    public static boolean isInstall(Context context, String appPkg) {
        if (!TextUtils.isEmpty(appPkg)) {
            PackageManager pm = context.getApplicationContext().getPackageManager();
            List<PackageInfo> allApps = pm.getInstalledPackages(0); // 获取本地所有已经安装的应用
            int size = (allApps == null ? 0 : allApps.size());
            for (int i = 0; i < size; i++) {
                PackageInfo info = allApps.get(i);
                if (appPkg.equals(info.packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
