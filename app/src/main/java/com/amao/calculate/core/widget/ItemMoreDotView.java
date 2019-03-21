//package com.amao.calculate.core.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.amao.calculate.utils.AppUtils;
//
//
///**
// * 带点的消息
// * <br/>
// * <li>Author 毛该得
// * <li>Email maogaide@vargo.com.cn
// * <li>Date 2018/3/7 15:00
// */
//public class ItemMoreDotView extends RelativeLayout {
//
//    private TextView text_title;
//    private View view_tip;
//
//    public ItemMoreDotView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//
//        LayoutInflater.from(context).inflate(R.layout.layout_item_more_dot, this, true);
//        this.text_title = (TextView) findViewById(R.id.text_title);
//        this.view_tip = findViewById(R.id.view_tip);
//
//        setMinimumHeight(AppUtils.getDimens(R.dimen.item_height));
//        setBackgroundResource(R.color.white);
//
//        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemMoreDotView);
//        if (attributes == null) return;
//
//        String text = attributes.getString(R.styleable.ItemMoreDotView_title);
//        if (!TextUtils.isEmpty(text)) {
//            text_title.setText(text);
//        }
//        boolean showTip = attributes.getBoolean(R.styleable.ItemMoreDotView_show_tip, false);
//        setViewTipShow(showTip);
//
//        int paddingStart = AppUtils.getDimens(R.dimen.item_padding_start);
//        boolean fillLine = attributes.getBoolean(R.styleable.ItemMoreDotView_line_fill, false);
//        if (fillLine) {
//            int paddingLeft = getPaddingLeft() > 0 ? getPaddingLeft() : paddingStart;
//            text_title.setPadding(paddingLeft, text_title.getPaddingTop(), text_title.getPaddingRight(), text_title.getPaddingBottom());
//        } else {
//            setPadding(paddingStart, getPaddingTop(), getPaddingRight(), getPaddingBottom());
//        }
//        attributes.recycle();
//    }
//
//    public void setTitle(String text) {
//        text_title.setText(text);
//    }
//
//    public void setTitle(int resId) {
//        text_title.setText(resId);
//    }
//
//    public void setViewTipShow(boolean visibility) {
//        int show = visibility ? VISIBLE : GONE;
//        view_tip.setVisibility(show);
//    }
//
//    public void setViewTipShow(int visibility) {
//        view_tip.setVisibility(visibility);
//    }
//}
