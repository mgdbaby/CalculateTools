package com.amao.calculate.utils;


import com.amao.calculate.core.DataCourier;

import org.greenrobot.eventbus.EventBus;
import org.xutils.common.util.LogUtil;
import org.xutils.x;


public class MsgUtils {

    private static String generateTag(){
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();

        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        return StringUtils.f(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
    }

    public static void send(int what, Object... obj){
        if(x.isDebug()){
            String tag = generateTag();
            LogUtil.i(StringUtils.f("Send message by Origin = %s, what = %s", tag, ConstantUtil.getOptWhatName(what)));
        }
        if(what != OptWhat.NONE){
            send_(true, what, obj);
        }
    }

    public static void send_(int what, Object... obj){
        send_(false, what, obj);
    }

    private static void send_(boolean showLog, int what, Object... obj){
        DataCourier courier = new DataCourier(what, obj);
        courier.setShowLog(showLog);
        EventBus.getDefault().post(courier);
    }
}
