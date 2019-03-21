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
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.amao.calculate.utils.AppUtils;
//
//import org.xutils.x;
//
//
///**
// * 带开关的条目
// * <br/>
// * <li>Author DaMao
// * <li>Email luzhiyong@vargo.com.cn
// * <li>Date 18/1/25 14:34
// */
//public class ItemSwitchView extends FrameLayout implements View.OnClickListener {
//
//    private TextView text_title;
//    private ImageView img_switch;
//
//    private boolean isChecked;
//    private Listener listener;
//
//    public ItemSwitchView(@NonNull Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//
//        LayoutInflater.from(context).inflate(R.layout.layout_item_switch, this, true);
//        this.text_title = (TextView) findViewById(R.id.text_title);
//        this.img_switch = (ImageView) findViewById(R.id.img_switch);
//
//        setMinimumHeight(AppUtils.getDimens(R.dimen.item_height));
//        setBackgroundResource(R.color.white);
//
//        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.ItemSwitchView);
//        if(attributes == null) return;
//
//        String text = attributes.getString(R.styleable.ItemSwitchView_title);
//        if(!TextUtils.isEmpty(text)){
//            text_title.setText(text);
//        }
//
//        this.isChecked = attributes.getBoolean(R.styleable.ItemSwitchView_checked, true);
//        int resId = (isChecked ? R.drawable.turn_on : R.drawable.turn_off);
//        img_switch.setImageResource(resId);
//        img_switch.setOnClickListener(this);
//
//        int paddingStart = AppUtils.getDimens(R.dimen.item_padding_start);
//        boolean fillLine = attributes.getBoolean(R.styleable.ItemMoreView_line_fill, false);
//        if(fillLine){
//            int paddingLeft = getPaddingLeft() > 0 ? getPaddingLeft() : paddingStart;
//            text_title.setPadding(paddingLeft, text_title.getPaddingTop(), text_title.getPaddingRight(), text_title.getPaddingBottom());
//        } else {
//            setPadding(paddingStart, getPaddingTop(), getPaddingRight(), getPaddingBottom());
//        }
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
//    @Override
//    public void setOnClickListener(@Nullable OnClickListener l) {
//        img_switch.setOnClickListener(l);
//    }
//
//    public void setListener(Listener listener) {
//        this.listener = listener;
//    }
//
//    public void setChecked(final boolean checked) {
//        x.task().autoPost(new Runnable() {
//            @Override
//            public void run() {
//                synchronized (ItemSwitchView.this) {
//                    isChecked = checked;
//                    int resId = (isChecked ? R.drawable.turn_on : R.drawable.turn_off);
//                    img_switch.setImageResource(resId);
//                }
//            }
//        });
//    }
//
//    public boolean isChecked(){
//        return isChecked;
//    }
//
//    @Override
//    public void onClick(View v) {
//        synchronized (this){
//            this.isChecked = !isChecked;
//            int resId = (isChecked ? R.drawable.turn_on : R.drawable.turn_off);
//            img_switch.setImageResource(resId);
//            if(listener != null){
//                listener.onChange(isChecked);
//            }
//        }
//    }
//
//    public interface Listener {
//        void onChange(boolean isChecked);
//    }
//
//}
