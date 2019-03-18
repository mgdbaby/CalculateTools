package com.amao.calculate.core;

import android.support.annotation.Nullable;
import android.view.View;

import java.util.List;

public abstract class ListViewHolder extends BaseViewHolder {

    public ListViewHolder(View itemView) {
        super(itemView);
    }

    public abstract <T extends BaseVO> void loadViewData(int pos, T data);

    protected <T extends BaseVO> void updateViewData(int pos, T data, List<Object> payloads) {

    }

    @SuppressWarnings("unchecked")
    @Nullable
    protected <T extends BaseVO> T getItem(int pos) {
        VRecyclerAdapter<T> adapter = (VRecyclerAdapter<T>) getAdapter();
        return adapter.getItem(pos);
    }

    protected <T extends BaseVO> T getItem() {
        return getItem(getAdapterPosition());
    }


}
