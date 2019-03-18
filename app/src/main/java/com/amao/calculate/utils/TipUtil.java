package com.amao.calculate.utils;


import android.view.Gravity;
import android.widget.Toast;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;


public class TipUtil {

    private static Toast toast;

    private synchronized static void show(final String msg, final int duration) {
        x.task().autoPost(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(x.app(), msg, duration);
                    int py = DensityUtil.dip2px(200);
                    toast.setGravity(Gravity.CENTER, 0, py);
                    toast.show();
                } else {
                    toast.setDuration(duration);
                    toast.setText(msg);
                    toast.show();
                }
            }
        });
    }

    /**
     * 显示短时间toast信息
     */
    public static void showShort(final String msg) {
        show(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示长时间toast信息
     */
    public static void showLong(String msg) {
        show(msg, Toast.LENGTH_LONG);
    }

    public static void showShortObj(Object obj) {
        showShort(obj == null ? "???" : obj.toString());
    }

    public static void showShort(int stringId, Object... args) {
        showShort(AppUtils.getString(stringId, args));
    }
}
