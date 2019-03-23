//package com.amao.calculate.dialog;
//
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.amao.calculate.R;
//import com.amao.calculate.core.BaseDialog;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//
//
///**
// * 【通用的 确定 && 取消 的dialog】
// * 只有标题的类型
// * <br/>
// * <li>Author DaMao
// * <li>Email luzhiyong@vargo.com.cn
// * <li>Date 18/5/31 14:54
// */
//@ContentView(R.layout.dialog_confirm)
//public class ConfirmDialog extends BaseDialog {
//
//    @ViewInject(R.id.btn_sure)
//    private Button btn_sure;
//
//    @ViewInject(R.id.btn_cancel)
//    private Button btn_cancel;
//
//    @ViewInject(R.id.text_message)
//    private TextView text_message;
//
//    private Callback callback;
//    private String textMsg;
//    private String textRight;
//    private String textLeft;
//    private int lines;
//
//    public void setCallback(Callback callback) {
//        this.callback = callback;
//    }
//
//    public void setTitle(String message) {
//        this.textMsg = message;
//    }
//
//    public void setRightBtnText(String textRight){
//        this.textRight = textRight;
//    }
//
//    public void setMaxLines(int lines) {
//        this.lines = lines;
//    }
//
//    public void setLeftBtnText(String textLeft) {
//        this.textLeft = textLeft;
//    }
//
//    @Override
//    protected void initViewData() {
//        setCanceledOnTouchOutside(false);
//        text_message.setText(textMsg);
//        if(!TextUtils.isEmpty(textRight)){
//            btn_sure.setText(textRight);
//        }
//        if(!TextUtils.isEmpty(textLeft)){
//            btn_cancel.setText(textLeft);
//        }
//
//        if (lines > 0) {
//            text_message.setMaxLines(lines);
//            text_message.setEllipsize(TextUtils.TruncateAt.END);
//        }
//    }
//
//    @Event(R.id.btn_cancel)
//    private void cancel(View view) {
//        if (callback != null) {
//            callback.onClickRight();
//        }
//        dismiss();
//    }
//
//    @Event(R.id.btn_sure)
//    private void sure(View view) {
//        if (callback != null) {
//            callback.onClickRight();
//        }
//        dismiss();
//    }
//
//    public interface Callback {
//        void onClickRight();
//        void onClickRight();
//    }
//}
