package com.amao.calculate.core;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amao.calculate.width.SlideLayout;

import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;


public class VRecyclerAdapter<T extends BaseVO> extends RecyclerView.Adapter<ListViewHolder> {

    protected Context context;
    private LayoutInflater inflater;
    private ImageOptions imageOptions;
    private Object extendData;
    private List<T> dataList;
    private SparseArray<Class<? extends ListViewHolder>> viewHolders;

    private SlideLayout.OnSlideListener onSlideListener;
    private boolean isSlideOpen;

    public Context getContext(){
        return context;
    }

    public VRecyclerAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public VRecyclerAdapter(Context context, List<T> dataList) {
        this(context);
        this.dataList = dataList;
    }

    public void setOnSlideListener(SlideLayout.OnSlideListener onSlideListener) {
        this.onSlideListener = onSlideListener;
    }

    public boolean isSlideOpen() {
        return isSlideOpen;
    }

    public void setData(List<T> dataList) {
        this.dataList = dataList;
    }

    public List<T> getData(){
        return dataList == null ? new ArrayList<T>() : dataList;
    }

    public void setExtendData(Object data) {
        this.extendData = data;
    }

    public void setImageOptions(ImageOptions imageOptions) {
        this.imageOptions = imageOptions;
    }

    public void setViewHolder(Class<? extends ListViewHolder> viewHolder) {
        addViewHolder(0, viewHolder);
    }

    public void addViewHolder(int viewType, Class<? extends ListViewHolder> viewHolder) {
        if (viewHolders == null) {
            viewHolders = new SparseArray<>();
        }
        viewHolders.put(viewType, viewHolder);
    }

    public void update(List<T> dataArray) {
        this.dataList = dataArray;
        notifyDataSetChanged();
    }

    public void update(List<T> dataArray, DiffUtil.DiffResult result) {
        this.dataList = dataArray;
        result.dispatchUpdatesTo(this);
    }

    public void remove(int position) {
        if (dataList != null && dataList.size() > position) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return (dataList == null ? 0 : dataList.size());
    }

    @Override
    public int getItemViewType(int position) {
        BaseVO vo = dataList.get(position);
        return vo.getViewType();
    }

    @Override
    public final ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewHolders == null) {
            throw new RuntimeException("RecyclerAdapter not set viewHolder.");
        }

        Class<? extends ListViewHolder> cls = viewHolders.get(viewType);
        if (cls == null) {
            throw new RuntimeException("The viewType=" + viewType + " BaseViewHolder was not found.");
        }

        try {
            ContentView contentView = cls.getAnnotation(ContentView.class);
            if (contentView == null) {
                throw new RuntimeException(String.format("View holder '%s' not annotation ContentView.", cls.getName()));
            }

            View view = inflater.inflate(contentView.value(), parent, false);

            return cls.getConstructor(View.class).newInstance(view);
        } catch (Exception e) {
            LogUtil.e("Create view holder error.", e);
            throw new RuntimeException("Create view holder error. class = " + cls.getName(), e);
        }
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, final int position) {
        holder.setExtendData(extendData);
        holder.setAdapter(this);
        holder.setImageOptions(imageOptions);
        holder.loadViewData(position, getItem(position));

        // 设置侧滑事件
        if(holder.itemView instanceof SlideLayout){
            SlideLayout layout = (SlideLayout) holder.itemView;

            layout.setCanSlide(holder.canSlide());
            if(holder.canSlide()){
                layout.setOnSlideListener(new SlideLayout.OnSlideListener() {
                    @Override
                    public void onSlide(boolean isOpen) {
                        isSlideOpen = isOpen;
                        if(onSlideListener != null){
                            onSlideListener.onSlide(isOpen);
                        }
                    }
                });
            }
        }
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position, List<Object> payloads) {
        if (payloads == null || payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            holder.setExtendData(extendData);
            holder.setAdapter(this);
            holder.setImageOptions(imageOptions);

            holder.updateViewData(position, getItem(position), payloads);
        }
    }

    @Override
    public void onViewRecycled(ListViewHolder holder) {
        holder.onViewRecycled();
    }

    protected boolean isLast(int pos) {
        return pos == getItemCount() - 1;
    }

    public T getItem(int pos) {
        if(pos < 0 || pos >= getItemCount()){
            return null;
        }
        T vo = dataList.get(pos);
        if (vo.needSetLast()) {
            vo.setLast(isLast(pos));
        }
        return vo;
    }

    /**
     * 删除Item
     *
     * @param position
     */
    public void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

}
