package com.amao.calculate.core;

/***
 * 消息事件类
 */
public class DataCourier {

    private Object[] data;
    private int optWhat;
    private boolean showLog = true;

    public DataCourier(int optWhat, Object... data) {
        this.optWhat = optWhat;
        this.data = data;
    }

    public int what(){
        return optWhat;
    }

    public Object[] getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    public <T> T getFirstData(){
        if(data != null && data.length > 0){
            return (T) data[0];
        }
        return null;
    }

    public boolean isShowLog() {
        return showLog;
    }

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }
}
