//package com.amao.calculate.dialog;
//
//import android.view.Gravity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//
//import cn.com.vargo.vtransfer.R;
//import cn.com.vargo.vtransfer.core.BaseDialog;
//import cn.com.vargo.vtransfer.utils.AppUtils;
//
///**
// * 带title + msg的确认、取消按钮弹框
// * <br/>
// * <li>Author 李东
// * <li>Email lidong@vargo.com.cn
// * <li>Date 2018/2/26 16:04
// */
//
//@ContentView(R.layout.dialog_confirm_title)
//public class ConfirmTitleDialog extends BaseDialog {
//
//    @ViewInject(R.id.btn_cancel)
//    private Button btn_cancel;
//
//    @ViewInject(R.id.btn_sure)
//    private Button btn_sure;
//
//    @ViewInject(R.id.text_title)
//    private TextView text_title;
//
//    @ViewInject(R.id.text_msg)
//    private TextView text_msg;
//
//    private Callback callback;
//    public String title,textMsg,textLeft,textRight;
//    public int gravity; //  Gravity.CENTER_VERTICAL 从左向右展示; Gravity.CENTER居中展示
//
//    public void setCallback(Callback callback) {
//        this.callback = callback;
//    }
//
//    public void setMessage(String title, String msg) {
//        this.title = title;
//        this.textMsg = msg;
//        this.textLeft = AppUtils.getString(R.string.cancel);
//        this.textRight = AppUtils.getString(R.string.sure);
//        this.gravity = Gravity.CENTER;
//    }
//
//    public void setMessage(String title, String msg, String textRight){ //右
//        this.title = title;
//        this.textMsg = msg;
//        this.textLeft = AppUtils.getString(R.string.cancel);
//        this.textRight = textRight;
//        this.gravity = Gravity.CENTER;
//    }
//
//    public void setMessage(String title, String msg, String textLeft, String textRight, int gravity){
//        this.title = title;
//        this.textMsg = msg;
//        this.textLeft = textLeft;
//        this.textRight = textRight;
//        this.gravity = gravity;
//    }
//
//    @Override
//    protected void initViewData() {
//        text_title.setGravity(gravity);
//        text_title.setText(title);
//        text_msg.setText(textMsg);
//        btn_sure.setText(textRight);
//        btn_cancel.setText(textLeft);
//    }
//
//    @Event(R.id.btn_cancel)
//    private void cancer(View view){
//        if (callback != null) {
//            callback.onClickRight();
//        }
//        dismiss();
//    }
//
//    @Event(R.id.btn_sure)
//    private void sure(View view){
//        if (callback != null) {
//            callback.onClickRight();
//        }
//        dismiss();
//    }
//
//
//    public interface Callback {
//        void onClickRight();
//
//        void onClickRight();
//    }
//
//}
