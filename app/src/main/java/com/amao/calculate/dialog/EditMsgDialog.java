package com.amao.calculate.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amao.calculate.R;
import com.amao.calculate.core.BaseDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 标题-编辑框-确认取消
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/23 14:27
 */
@ContentView(R.layout.dialog_edit_msg)
public class EditMsgDialog extends BaseDialog {

    @ViewInject(R.id.text_title)
    private TextView textTitle;

    @ViewInject(R.id.edit_text)
    private EditText editText;

    @ViewInject(R.id.btn_left)
    private Button btnLeft;

    @ViewInject(R.id.btn_right)
    private Button btnRight;

    private Callback callback;
    private String title;
    private String hintText;
    private String leftText;
    private String rightText;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected void initViewData() {
        if (!TextUtils.isEmpty(title)) textTitle.setText(title);
        if (!TextUtils.isEmpty(hintText)) editText.setHint(hintText);
        if (!TextUtils.isEmpty(leftText)) btnLeft.setText(leftText);
        if (!TextUtils.isEmpty(rightText)) btnRight.setText(rightText);
    }

    @Event(R.id.btn_left)
    private void onClickLeft(View view) {
        dismiss();
    }

    @Event(R.id.btn_right)
    private void onClickRight(View view) {
//        dismiss();
        if (callback != null) {
            String mobile = editText.getText().toString();
            callback.onClickRight(mobile);
        }
    }

    public void setMessage(String title, String hintText, String leftText, String rightText) {
        this.title = title;
        this.hintText = hintText;
        this.leftText = leftText;
        this.rightText = rightText;
    }
}
