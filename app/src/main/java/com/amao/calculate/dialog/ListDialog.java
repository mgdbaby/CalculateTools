//package com.amao.calculate.dialog;
//
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//
//import com.amao.calculate.R;
//import com.amao.calculate.core.BaseDialog;
//import com.amao.calculate.core.VRecyclerAdapter;
//
//import org.xutils.view.annotation.ContentView;
//import org.xutils.view.annotation.ViewInject;
//
//import java.util.ArrayList;
//
//
///**
// * 通用的List类型的dialog
// * <br/>
// * <li>Author 李玲
// * <li>Email liling@vargo.com.cn
// * <li>Date 2017/11/29 19:56
// */
//
//@ContentView(R.layout.layout_dialog_list)
//public class ListDialog extends BaseDialog {
//
//    public List<DialogItemEntity> itemList;
//
//    @ViewInject(R.id.dialog_rv)
//    private RecyclerView dialog_rv;
//
//    private VRecyclerAdapter<DialogItemEntity> mDialogAdapter;
//
//    private ListDialog.OnClickListener onClickListener;
//
//    public void addItem(DialogItemEntity entity) {
//        if (itemList == null) {
//            itemList = new ArrayList<>();
//        }
//        itemList.add(entity);
//    }
//
//    @Override
//    protected void initViewData() {
//        if (mDialogAdapter == null) {
//            //初始化适配器
//            mDialogAdapter = new VRecyclerAdapter<>(getActivity(), itemList);
//            mDialogAdapter.setViewHolder(DialogViewHolder.class);
//            mDialogAdapter.setExtendData(this);
//            dialog_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
//            dialog_rv.setAdapter(mDialogAdapter);
//        } else {
//            mDialogAdapter.update(itemList);
//        }
//    }
//
//    public void setOnClickListener(OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }
//
//    public OnClickListener getOnClickListener() {
//        return onClickListener;
//    }
//
//    public abstract static class OnClickListener {
//
//        public void onPreFinish(BaseDialog dialog){
//            if(dialog != null){
//                dialog.dismiss();
//            }
//        }
//
//        public abstract void onDialogItemClick(BaseDialog dialog, int pos);
//    }
//}
