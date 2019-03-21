package com.amao.calculate.bean;

import android.graphics.drawable.Drawable;

import com.amao.calculate.AppConfig;
import com.amao.calculate.core.BaseVO;
import com.amao.calculate.utils.AppUtils;

import java.io.Serializable;

public class DataBean extends BaseVO implements Serializable {

    private int value;              // 数据
    private int bgResId;            // 数据背景
    private int type;               // 数据类型  0 数字，后面拓展
    private int money;              // 钱

    public DataBean(int value, int bgResId, int selectType) {
        this.value = value;
        this.type = selectType;
        this.bgResId = bgResId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getBgResId() {
        return bgResId;
    }

    public void setBgResId(int bgResId) {
        this.bgResId = bgResId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMoney() {
        return money;
    }

    public String getMoneyStr() {
        return String.valueOf(money);
    }

    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * 获取背景内容
     */
    public Drawable getDrawable() {
        return AppUtils.getDrawable(bgResId);
    }

    /**
     * 获取显示内容
     */
    public String getContent() {
        String showStr = "未知";
        if (type == AppConfig.OrderType.TYPE_NUM) {
            if (value < 10) {
                showStr = "0" + value;
            } else {
                showStr = String.valueOf(value);
            }
        }
        return showStr;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "value=" + value +
                ", bgResId=" + bgResId +
                ", type=" + type +
                ", money=" + money +
                '}';
    }
}