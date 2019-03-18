package com.amao.calculate.core;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.xutils.image.ImageOptions;
import org.xutils.x;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    private Object extendData;
    protected ImageOptions imageOptions;
    private RecyclerView.Adapter adapter;

    public BaseViewHolder(View itemView) {
        super(itemView);
        x.view().inject(this, itemView);
    }

    protected void handleLastLineView(View lineView, int margin) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) lineView.getLayoutParams();
        layoutParams.leftMargin = margin;
        lineView.setLayoutParams(layoutParams);
    }

    public void onViewRecycled() {

    }

    public void setExtendData(Object extendData) {
        this.extendData = extendData;
    }

    public <T> T getExtendData() {
        return getExtendData(null);
    }

    @SuppressWarnings("unchecked")
    public <T> T getExtendData(T defVal) {
        return extendData == null ? defVal : (T) extendData;
    }

    protected RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public boolean canSlide() {
        return false;
    }

    public void setImageOptions(ImageOptions imageOptions) {
        this.imageOptions = imageOptions;
    }

}
