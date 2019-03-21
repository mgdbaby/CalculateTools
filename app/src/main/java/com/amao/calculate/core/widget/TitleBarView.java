package com.amao.calculate.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.amao.calculate.R;
import com.amao.calculate.utils.AppUtils;

import org.xutils.common.util.DensityUtil;


/**
 * 标题栏组件
 * <br/>
 * <li>Author DaMao
 * <li>Email luzhiyong@vargo.com.cn
 * <li>Date 18/1/25 10:42
 */
public class TitleBarView extends FrameLayout {

    public static final int LEFT_ID = R.id.btn_left;
    public static final int RIGHT_ID = R.id.btn_right;
    private static final int PADDING_VALUE = DensityUtil.dip2px(10);

    private Button btn_left;
    private Button btn_right;
    private TextView text_title;
    private View view_line;

    public TitleBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this, true);
        btn_left = (Button) findViewById(LEFT_ID);
        btn_right = (Button) findViewById(RIGHT_ID);
        text_title = (TextView) findViewById(R.id.text_title);
        view_line = (View) findViewById(R.id.view_line);

        setBackgroundResource(R.color.title_bar_bg);
        setMinimumHeight(AppUtils.getDimens(R.dimen.title_bar_height));

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
        if (attributes == null) return;
        initButton(attributes, btn_left,
                R.styleable.TitleBarView_left_text,
                R.styleable.TitleBarView_left_img,
                R.styleable.TitleBarView_left_visible,
                R.drawable.xml_btn_back_bg);
        initButton(attributes, btn_right,
                R.styleable.TitleBarView_right_text,
                R.styleable.TitleBarView_right_img,
                R.styleable.TitleBarView_right_visible,
                R.drawable.xml_btn_add_bg);

        String text = attributes.getString(R.styleable.TitleBarView_title_text);
        if (!TextUtils.isEmpty(text)) {
            text_title.setText(text);
        }
        attributes.recycle();
    }

    private void initButton(TypedArray attributes, Button button, int textIdx, int imgIdx, int visibleIdx, int defBgResId) {
        if (attributes == null) return;
        boolean btnVisible = attributes.getBoolean(visibleIdx, true);
        if (!btnVisible) {
            button.setVisibility(GONE);
            return;
        }

        String text = attributes.getString(textIdx);
        if (!TextUtils.isEmpty(text)) {
            button.setText(text);
            return;
        }

        int bgResId = attributes.getResourceId(imgIdx, defBgResId);
        if (bgResId > 0) {
            button.setBackgroundResource(bgResId);
        }
    }

    public void setLeftVisible(int visibility) {
        btn_left.setVisibility(visibility);
    }

    public void setRightVisible(int visibility) {
        btn_right.setVisibility(visibility);
    }

    public void setLineVisible(int visibility) {
        view_line.setVisibility(visibility);
    }

    public void setLeftEnabled(boolean enabled) {
        btn_left.setEnabled(enabled);
    }

    public void setRightEnabled(boolean enabled) {
        btn_right.setEnabled(enabled);
    }

    public void setTitleText(int resId) {
        text_title.setText(resId);
    }

    public void setTitleColor(int resId) {
        text_title.setTextColor(resId);
    }

    public void setTitleText(String text) {
        text_title.setText(text);
    }

    public void setLeftText(int resId) {
        btn_left.setText(resId);
        btn_left.setBackground(null);
        btn_left.setPadding(PADDING_VALUE, 0, PADDING_VALUE, 0);
    }

    public void setLeftText(String text) {
        btn_left.setText(text);
        btn_left.setBackground(null);
        btn_left.setPadding(PADDING_VALUE, 0, PADDING_VALUE, 0);
    }

    public void setLeftIcon(int resId) {
        btn_left.setText("");
        btn_left.setBackgroundResource(resId);
    }

    public void setRightText(int resId) {
        btn_right.setText(resId);
        btn_right.setBackground(null);
        btn_right.setPadding(PADDING_VALUE, 0, PADDING_VALUE, 0);
    }

    public void setRightText(String text) {
        btn_right.setText(text);
        btn_right.setBackground(null);
        btn_right.setPadding(PADDING_VALUE, 0, PADDING_VALUE, 0);
    }

    public void setRightIcon(int resId) {
        btn_right.setText("");
        btn_right.setBackgroundResource(resId);
    }

}
