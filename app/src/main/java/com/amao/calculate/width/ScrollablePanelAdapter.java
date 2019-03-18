package com.amao.calculate.width;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amao.calculate.R;

import java.util.List;

public class ScrollablePanelAdapter extends PanelAdapter {

    private static final int TYPE_NUM = 0;
    private static final int TYPE_NAME = 1;
    private static final int TYPE_ORDER = 2;
    private static final int TITLE_TYPE = 4;

    private List<StringBean> numList;
    private List<StringBean> personList;
    private List<List<StringBean>> ordersList;
    private Context context;

    public ScrollablePanelAdapter(Context context, List<StringBean> numList, List<StringBean> personList, List<List<StringBean>> ordersList) {
        this.numList = numList;
        this.personList = personList;
        this.ordersList = ordersList;
        this.context = context;
    }

    @Override
    public int getRowCount() {
        return numList.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return personList.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int row, int column) {
        int viewType = getItemViewType(row, column);
        switch (viewType) {
            case TYPE_NAME:
                setRowColumnView((TextViewHolder) holder, column, true);
                break;
            case TYPE_NUM:
                setRowColumnView((TextViewHolder) holder, row, false);
                break;
            case TYPE_ORDER:
                setOrderView(row, column, (TextViewHolder) holder);
                break;
            case TITLE_TYPE:
                break;
            default:
                setOrderView(row, column, (TextViewHolder) holder);
        }
    }

    public int getItemViewType(int row, int column) {
        if (column == 0 && row == 0) {
            return TITLE_TYPE;
        }
        if (column == 0) {
            return TYPE_NUM;
        }
        if (row == 0) {
            return TYPE_NAME;
        }
        return TYPE_ORDER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_NAME:
            case TYPE_NUM:
                return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_room_info, parent, false));
            case TYPE_ORDER:
                return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_order_info, parent, false));
            case TITLE_TYPE:
                return new TitleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_title, parent, false));
            default:
                break;
        }
        return new TextViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_order_info, parent, false));
    }

    private void setRowColumnView(TextViewHolder viewHolder, int pos, boolean isColumn) {
        Drawable drawable = AppCompatResources.getDrawable(context, R.drawable.bg_room_blue_middle);
        viewHolder.content.setBackground(drawable);
        StringBean stringBean;
        if (pos > 0 && (stringBean = isColumn ? personList.get(pos - 1) : numList.get(pos - 1)) != null) {
            viewHolder.content.setText(stringBean.getContent());
        } else {
            viewHolder.content.setText("");
        }
    }

    private void setOrderView(final int row, final int column, TextViewHolder viewHolder) {
        StringBean orderInfo = ordersList.get(row - 1).get(column - 1);
        if (orderInfo != null) {
            final String money = orderInfo.getContent();
            if (TextUtils.isEmpty(money)) {
                viewHolder.itemView.setClickable(false);
                viewHolder.content.setText("");
                viewHolder.content.setBackground(null);
                viewHolder.itemView.setOnClickListener(null);
            } else {
                viewHolder.itemView.setClickable(true);
                Drawable drawable = AppCompatResources.getDrawable(context, R.drawable.bg_room_red_middle);
                viewHolder.content.setBackground(drawable);
                viewHolder.content.setText(money);
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "￥" + money, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            viewHolder.itemView.setClickable(false);
            viewHolder.content.setText("");
            viewHolder.content.setBackground(null);
            viewHolder.itemView.setOnClickListener(null);
        }
    }

    private static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView content;

        public TextViewHolder(View view) {
            super(view);
            this.content = view.findViewById(R.id.text_content);
        }
    }

    private static class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;

        public TitleViewHolder(View view) {
            super(view);
            this.titleTextView = view.findViewById(R.id.title);
        }
    }
}
