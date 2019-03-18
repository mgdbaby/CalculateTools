package com.amao.calculate.core;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwitchCase {

    int POSTING = 100000000;         // 事件处理,和事件发送在相同线程
    int MAIN = 200000000;            // 事件在主线程(UI线程)中处理,不能处理耗时任务
    int BACKGROUND = 300000000;      // 在一个队列子线程中处理,处理时间不宜太久
    int ASYNC = 400000000;           // 在单独线程中处理,有线程池,但需要控制线程的数量

    int[] value();

    int threadMode() default MAIN;

    String info();

}
