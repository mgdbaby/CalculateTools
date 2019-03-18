//package com.amao.calculate.dialog;
//
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.Event;
//import org.xutils.view.annotation.ViewInject;
//
//import cn.com.vargo.vtransfer.R;
//import cn.com.vargo.vtransfer.core.BaseDialog;
//
///**
// * 关闭提示对话框
// * <br/>
// * <li>Author 陈湃
// * <li>Email chenpai@vargo.com.cn
// * <li>Date 2018/6/14 10:30
// */
//@ContentView(R.layout.dialog_close_tip)
//public class CloseTipDialog extends BaseDialog {
//    @ViewInject(R.id.btn_cancel)
//    private Button btn_cancel;
//
//    @ViewInject(R.id.btn_close)
//    private Button btn_close;
//
//    @ViewInject(R.id.linearlayout_selector)
//    private LinearLayout linearlayout_selector;
//
//    @ViewInject(R.id.img_selector)
//    private ImageView img_selector;
//
//    private Callback callback;
//    private boolean checked;
//
//    @Override
//    protected void initViewData() {
//        setCanceledOnTouchOutside(false);
//    }
//
//    public void setCallback(Callback callback){
//        this.callback = callback;
//    }
//
//    @Event(R.id.btn_cancel)
//    private void cancel(View view){
//        if(callback != null){
//            callback.onCancel();
//        }
//        dismiss();
//    }
//
//    @Event(R.id.btn_close)
//    private void close(View view){
//        if(callback != null){
//            callback.onClose();
//        }
//        dismiss();
//    }
//
//    @Event(R.id.linearlayout_selector)
//    private void selector(View view){
//        if(isChecked()){
//            setChecked(false);
//            img_selector.setImageResource(R.drawable.ic_sel_no);
//        }else {
//            setChecked(true);
//            img_selector.setImageResource(R.drawable.ic_sel_yes);
//        }
//
//    }
//
//    public boolean isChecked(){
//        return checked;
//    }
//
//    public void setChecked(boolean checked) {
//        this.checked = checked;
//    }
//
//    public interface Callback{
//        void onCancel();
//        void onClose();
//    }
//}
