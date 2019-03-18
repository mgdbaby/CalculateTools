package com.amao.calculate.core;

import android.support.annotation.Nullable;
import android.support.v7.util.ListUpdateCallback;

import org.xutils.common.util.LogUtil;
import org.xutils.x;

public abstract class BaseDiff extends DiffUtil.Callback implements ListUpdateCallback {

    private int isChanged;

    @Override
    public final boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return areItemsTheSame0(oldItemPosition, newItemPosition);
    }

    @Override
    public final boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return areContentsTheSame0(oldItemPosition, newItemPosition);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return newItemPosition;
    }

    @Override
    public final void onInserted(int position, int count) {
        if (x.isDebug()) {
            LogUtil.d("onInserted pos = " + position + ", count = " + count);
        }
        this.isChanged += count;
        onInserted0(position, count);
    }

    @Override
    public final void onRemoved(int position, int count) {
        if (x.isDebug()) {
            LogUtil.d("onRemoved pos = " + position + ", count = " + count);
        }
        this.isChanged += count;
        onRemoved0(position, count);
    }

    @Override
    public final void onChanged(int position, int count, Object payload) {
        if (x.isDebug()) {
            LogUtil.d("onChanged pos = " + position + ", newPos = " + payload + ", count = " + count);
        }
        this.isChanged += count;
        onChanged0(position, count, payload);
    }

    @Override
    public final void onMoved(int fromPosition, int toPosition) {
        onMoved0(fromPosition, toPosition);
    }

    protected abstract boolean areItemsTheSame0(int oldItemPosition, int newItemPosition);

    protected abstract boolean areContentsTheSame0(int oldItemPosition, int newItemPosition);

    protected abstract void onInserted0(int position, int count);

    protected abstract void onRemoved0(int position, int count);

    protected abstract void onChanged0(int position, int count, Object payload);

    protected void onMoved0(int fromPosition, int toPosition){}

    public boolean isChanged() {
        return isChanged > 0;
    }

    protected void rollbackChange(){
        this.isChanged --;
    }
}
