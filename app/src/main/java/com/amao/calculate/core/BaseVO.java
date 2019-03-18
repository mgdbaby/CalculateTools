package com.amao.calculate.core;

import com.alibaba.fastjson.annotation.JSONField;

public abstract class BaseVO {

    private int isLast = -1;

    @JSONField(serialize = false)
    public int getViewType(){
        return 0;
    }

    public boolean needSetLast(){
        return isLast == -1;
    }

    @JSONField(serialize = false)
    public boolean isLast() {
        return isLast == 1;
    }

    public void setLast(boolean last) {
        this.isLast = (last ? 1 : 0);
    }

}
