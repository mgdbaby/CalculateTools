package com.amao.calculate.core;

import android.util.SparseArray;

import com.amao.calculate.utils.ConstantUtil;
import com.amao.calculate.utils.StringUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.lang.reflect.Method;


public class ReflexProxy {

    public static String getThreadName(int threadMode) {
        switch (threadMode) {
            case SwitchCase.POSTING:
                return "POSTING";
            case SwitchCase.MAIN:
                return "MAIN";
            case SwitchCase.BACKGROUND:
                return "BACKGROUND";
            case SwitchCase.ASYNC:
                return "ASYNC";
        }
        return String.valueOf(threadMode);
    }

    public static void handleMessage(Object handler, SparseArray<Method> methodMap, int threadMode, DataCourier courier){
        int mKey = methodKey(threadMode, courier.what());

        Method method = methodMap.get(mKey);
        if(method == null){
            return;
        }

        if(courier.isShowLog() && x.isDebug()){
            String clsName = handler.getClass().getName();
            String threadName = getThreadName(threadMode);
            String optWhat = ConstantUtil.getOptWhatName(courier.what());
            LogUtil.i(StringUtils.f("Class %s[%d] receive a message in thread %s ..., what = %s", clsName, handler.hashCode(), threadName, optWhat));
        }

        invokeCaseMethod(handler, method, mKey, courier.getData());
    }

    public static void loadCaseMethod(Object handler, SparseArray<Method> methodArray){
        Method[] methods = handler.getClass().getDeclaredMethods();
        int size = (methods == null ? 0 : methods.length);
        for (int i = 0; i < size; i++) {
            Method caseMethod = methods[i];
            SwitchCase switchCase = caseMethod.getAnnotation(SwitchCase.class);
            if(switchCase == null){
                continue;
            }

            if(x.isDebug()){
                LogUtil.d("Load " + handler.getClass().getName() + "." + caseMethod.getName());
            }

            caseMethod.setAccessible(true);
            int threadMode = switchCase.threadMode();
            int[] value = switchCase.value();
            for (int val : value) {
                int mKey = methodKey(threadMode, val);
                methodArray.put(mKey, caseMethod);
            }
        }
    }

    private static void invokeCaseMethod(Object handler, Method method, int what, Object... params){
        try {
            Class[] paramTypes = method.getParameterTypes();
            if(paramTypes == null || paramTypes.length == 0){
                method.invoke(handler);
            } else {
                int paramLen = (params == null ? 0 : params.length);
                Object[] args = new Object[paramTypes.length];
                for (int i = 0; i < args.length; i++) {
                    args[i] = (paramLen > i ? params[i] : null);
                }
                method.invoke(handler, args);
            }
        } catch (Exception e) {
            LogUtil.e(String.format("ReflexProxy invoke method error. %s.%s ", handler.getClass().getName(), method.getName()), e);
        }
    }

    // 构造key
    private static int methodKey(int threadMode, int what){
        return threadMode + what;
    }

}
