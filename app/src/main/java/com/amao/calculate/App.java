package com.amao.calculate;

import android.app.Application;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

/**
 * @TODO 写类注释
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/18 21:07
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        x.Ext.init(this);

        LogUtil.customTagPrefix = "Calculate";
        x.Ext.setDebug(true);
    }
}
