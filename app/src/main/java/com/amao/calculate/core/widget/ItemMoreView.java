//package com.amao.calculate.core.widget;
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//
//import com.amao.calculate.utils.AppUtils;
//
//
///**
// * 点击可进入下一级的条目，不带内容
// * <br/>
// * <li>Author DaMao
// * <li>Email luzhiyong@vargo.com.cn
// * <li>Date 18/1/25 14:34
// */
//public class ItemMoreView extends FrameLayout {
//
//    private TextView text_title;
//
//    public ItemMoreView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//
//        LayoutInflater.from(context).inflate(R.layout.layout_item_more, this, true);
//        this.text_title = (TextView) findViewById(R.id.text_title);
//
//        setMinimumHeight(AppUtils.getDimens(R.dimen.item_height));
//        setBackgroundResource(R.drawable.xml_selector_item_click_bg);
//
//        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemMoreView);
//        if(attributes == null) return;
//
//        String text = attributes.getString(R.styleable.ItemMoreView_title);
//        if(!TextUtils.isEmpty(text)){
//            text_title.setText(text);
//        }
//
//        int paddingStart = AppUtils.getDimens(R.dimen.item_padding_start);
//        boolean fillLine = attributes.getBoolean(R.styleable.ItemMoreView_line_fill, false);
//        if(fillLine){
//            int paddingLeft = getPaddingLeft() > 0 ? getPaddingLeft() : paddingStart;
//            text_title.setPadding(paddingLeft, text_title.getPaddingTop(), text_title.getPaddingRight(), text_title.getPaddingBottom());
//        } else {
//            setPadding(paddingStart, getPaddingTop(), getPaddingRight(), getPaddingBottom());
//        }
//
//        attributes.recycle();
//    }
//
//    public void setTitle(String text){
//        text_title.setText(text);
//    }
//
//    public void setTitle(int resId){
//        text_title.setText(resId);
//    }
//
//}
