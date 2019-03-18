//package com.amao.calculate.dialog;
//
//import android.widget.Button;
//import android.widget.TextView;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.ViewInject;
//
//import cn.com.vargo.vtransfer.R;
//
///**
// * 【通用的 确定 && 取消 的dialog】
// * 若有其他不同点，可对itemList的实体类进行修改
// * <br/>
// * <li>Author DaMao
// * <li>Email luzhiyong@vargo.com.cn
// * <li>Date 18/5/31 14:54
// */
//@ContentView(R.layout.dialog_confirm_message)
//public class ConfirmMessageDialog extends ConfirmDialog {
//
//    @ViewInject(R.id.text_message)
//    private TextView text_message;
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
//    private String message;
//    public String title, textMsg, textLeft, textRight;
//    public int gravity;
//
//    @Override
//    protected void initViewData() {
//        super.initViewData();
//
//        text_message.setText(message);
//        text_title.setText(title);
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    @Override
//    public void setMaxLines(int lines) {
//        // 标题默认1行
//        super.setMaxLines(1);
//    }
//
//    public void setMessage(String title, String msg, String textLeft, String textRight, int gravity) {
//        this.title = title;
//        this.textMsg = msg;
//        this.textLeft = textLeft;
//        this.textRight = textRight;
//        this.gravity = gravity;
//    }
//
//}
