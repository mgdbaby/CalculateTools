package com.amao.calculate.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amao.calculate.R;
import com.amao.calculate.activity.ContactListActivity;
import com.amao.calculate.activity.ContactNewActivity;
import com.amao.calculate.bean.DataBean;
import com.amao.calculate.core.BaseFragment;
import com.amao.calculate.core.SwitchCase;
import com.amao.calculate.core.VRecyclerAdapter;
import com.amao.calculate.core.widget.TitleBarView;
import com.amao.calculate.database.dao.ContactDao;
import com.amao.calculate.database.entity.ContactEntity;
import com.amao.calculate.dialog.Callback;
import com.amao.calculate.dialog.EditMsgDialog;
import com.amao.calculate.dialog.SelectNumDialog;
import com.amao.calculate.utils.OptWhat;
import com.amao.calculate.utils.StringUtils;
import com.amao.calculate.utils.TipUtil;
import com.amao.calculate.viewholder.DataListViewHolder;
import com.amao.calculate.width.DataItemDecoration;
import com.amao.calculate.width.SlideLayout;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息fragment
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/20 10:46
 */
@ContentView(R.layout.fragment_main_one)
public class PageOneFragment extends BaseFragment implements SlideLayout.OnSlideListener {

    @ViewInject(R.id.text_num)
    private TextView textNum;

    @ViewInject(R.id.text_no_data)
    private TextView textNoData;

    @ViewInject(R.id.text_contact_name)
    private TextView textContactName;

    @ViewInject(R.id.edit_money)
    private EditText editMoney;

    @ViewInject(R.id.title_bar)
    private TitleBarView titleBarView;

    @ViewInject(R.id.recycler_order)
    private RecyclerView recyclerOrder;

    private DataBean curDataBean;

    private VRecyclerAdapter<DataBean> orderAdapter;
    private List<DataBean> orderList;

    private int stage = 0;
    private int contactId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerHandler();
    }

    @Override
    protected void onCreateView(View view, Bundle savedInstanceState) {
        super.onCreateView(view, savedInstanceState);

        orderList = new ArrayList<>();
        //初始化适配器
        orderAdapter = new VRecyclerAdapter<>(getContext(), orderList);
        orderAdapter.setViewHolder(DataListViewHolder.class);
        orderAdapter.setOnSlideListener(this);
        //初始化列表控件
        recyclerOrder.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerOrder.setAdapter(orderAdapter);
        recyclerOrder.addItemDecoration(new DataItemDecoration());
        ((SimpleItemAnimator) recyclerOrder.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Event(TitleBarView.RIGHT_ID)
    private void onClickRight(View view) {
        toActivity(ContactNewActivity.class);
    }

    @Event(TitleBarView.LEFT_ID)
    private void onClickLeft(View view) {
        final EditMsgDialog dialog = buildDialog(EditMsgDialog.class);
        dialog.setMessage("设置期数", "请输入期数", null, null);
        dialog.setCallback(new Callback() {
            @Override
            public void onClickRight(Object object) {
                String data = (String) object;
                if (StringUtils.isNumeric(data)) {
                    stage = StringUtils.toInt(data);
                    titleBarView.setLeftText("第" + data + "期");
                    dialog.dismiss();
                } else {
                    TipUtil.showShort("抱歉，您输入的为非数字，请重新设置");
                }
            }

            @Override
            public void onClickLeft() {

            }
        });
        dialog.show();
    }

    @Event(R.id.layout_contact)
    private void onClickSelectContact(View view) {
        List<ContactEntity> contacts = ContactDao.findAllContacts();
        LogUtil.i("contacts : " + (contacts == null ? " null" : contacts.toString()));
        toActivity(ContactListActivity.class);
    }

    @Event(R.id.text_num)
    private void onClickNum(View view) {
        SelectNumDialog dialog = buildDialog(SelectNumDialog.class);
        dialog.setCancelable(true);
        dialog.show();
    }

    @Event(R.id.text_btn_confirm)
    private void onClickConfirm(View view) {
        String data = textNum.getText().toString();
        if (TextUtils.isEmpty(data) || "号码".equals(data)) {
            TipUtil.showShort("请先选择你要添加的号码");
            return;
        }

        String money = editMoney.getText().toString();
        if (TextUtils.isEmpty(money.trim())) {
            TipUtil.showShort("请输入金额");
            return;
        }

        curDataBean.setMoney(Integer.valueOf(money));
        LogUtil.i("======" + curDataBean.toString());
        orderList.add(curDataBean);
        updateAdapter();
    }

    @SwitchCase(value = OptWhat.SELECT_NUM, info = "选择号码")
    private void selectNum(DataBean dataBean) {
        if (dataBean != null) {
            curDataBean = dataBean;
            textNum.setText(dataBean.getContent());
        }
    }

    @SwitchCase(value = OptWhat.SELECT_CONTACT, info = "选择联系人")
    private void selectNum(ContactEntity entity) {
        if (entity != null) {
            textContactName.setText(entity.getName());
            contactId = entity.getId();
        }
    }

    private void updateAdapter() {
        if (orderList == null || orderList.isEmpty()) {
            recyclerOrder.setVisibility(View.GONE);
            textNoData.setVisibility(View.VISIBLE);
        } else {
            recyclerOrder.setVisibility(View.VISIBLE);
            textNoData.setVisibility(View.GONE);
            orderAdapter.update(orderList);
        }
    }

    @Override
    public void onSlide(boolean isOpen) {

    }
}
