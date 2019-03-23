//package com.amao.calculate.dialog;
//
//import android.view.View;
//import android.widget.TextView;
//
//import com.amao.calculate.R;
//import com.amao.calculate.core.BaseDialog;
//import com.amao.calculate.utils.AppUtils;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//
//
//
///***
// * 消息提示对话框
// */
//@ContentView(R.layout.dialog_message)
//public class MessageDialog extends BaseDialog {
//
//    @ViewInject(R.id.text_msg)
//    private TextView text_msg;
//
//    @ViewInject(R.id.text_btn_finish)
//    private TextView textFinish;
//
//    private Callback callback;
//    private String message;
//    private String strFinish;
//
//    public void setCallback(Callback callback) {
//        this.callback = callback;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//        this.strFinish = AppUtils.getString(R.string.i_know);
//    }
//
//    public void setMessage(String message, String strFinish) {
//        this.message = message;
//        this.strFinish = strFinish;
//    }
//
//    @Override
//    protected void initViewData() {
//        setCanceledOnTouchOutside(false);
//        text_msg.setText(message);
//        textFinish.setText(strFinish);
//    }
//
//    @Event(R.id.text_btn_finish)
//    private void onBtnFinish(View view) {
//        dismiss();
//        if (callback != null) {
//            callback.onClickRight();
//        }
//    }
//
//    public interface Callback {
//        void onClickRight();
//    }
//
//}
