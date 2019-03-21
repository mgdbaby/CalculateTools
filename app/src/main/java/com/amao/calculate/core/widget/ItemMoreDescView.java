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
//import org.xutils.common.util.DensityUtil;
//
//
///**
// * 点击可进入下一级的条目，带内容
// * <br/>
// * <li>Author DaMao
// * <li>Email luzhiyong@vargo.com.cn
// * <li>Date 18/1/25 14:34
// */
//public class ItemMoreDescView extends FrameLayout {
//
//    private TextView text_title;
//    private TextView text_content;
//
//    public ItemMoreDescView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//
//        LayoutInflater.from(context).inflate(R.layout.layout_item_more_desc, this, true);
//        this.text_title = (TextView) findViewById(R.id.text_title);
//        this.text_content = (TextView) findViewById(R.id.text_content);
//
//        setMinimumHeight(AppUtils.getDimens(R.dimen.item_height));
//        setBackgroundResource(R.color.white);
//        text_content.setMaxWidth((int) (DensityUtil.getScreenWidth() * 0.6F));
//
//        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemMoreView);
//        if(attributes == null) return;
//
//        String text = attributes.getString(R.styleable.ItemMoreView_title);
//        if(!TextUtils.isEmpty(text)){
//            text_title.setText(text);
//        }
//        text = attributes.getString(R.styleable.ItemMoreDescView_content);
//        if(!TextUtils.isEmpty(text)){
//            text_content.setText(text);
//        }
//
//        boolean fillLine = attributes.getBoolean(R.styleable.ItemMoreView_line_fill, false);
//        setLineFill(fillLine);
//
//        attributes.recycle();
//    }
//
//    public void setTitle(String text){
//        text_title.setText(text);
//    }
//
//    public void setContent(String text){
//        text_content.setText(text);
//    }
//
//    public void setTitle(int resId){
//        text_title.setText(resId);
//    }
//
//    public void setContent(int resId){
//        text_content.setText(resId);
//    }
//
//    public void setEnabled(final boolean enabled){
//        if(enabled){
//            AppUtils.textDrawableRight(text_title, R.drawable.ic_more);
//        } else {
//            AppUtils.textDrawableRight(text_title, null);
//        }
//    }
//
//    public void setLineFill(boolean isFill){
//        int paddingStart = AppUtils.getDimens(R.dimen.item_padding_start);
//        if(isFill){
//            int paddingLeft = getPaddingLeft() > 0 ? getPaddingLeft() : paddingStart;
//            text_title.setPadding(paddingLeft, text_title.getPaddingTop(), text_title.getPaddingRight(), text_title.getPaddingBottom());
//            setPadding(0, getPaddingTop(), getPaddingRight(), getPaddingBottom());
//        } else {
//            setPadding(paddingStart, getPaddingTop(), getPaddingRight(), getPaddingBottom());
//            text_title.setPadding(0, text_title.getPaddingTop(), text_title.getPaddingRight(), text_title.getPaddingBottom());
//        }
//    }
//
//}
