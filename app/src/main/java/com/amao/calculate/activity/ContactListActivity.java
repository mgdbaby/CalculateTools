package com.amao.calculate.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.TextView;

import com.amao.calculate.R;
import com.amao.calculate.core.BaseActivity;
import com.amao.calculate.core.SwitchCase;
import com.amao.calculate.core.VRecyclerAdapter;
import com.amao.calculate.core.widget.NotifyLinearLayoutManager;
import com.amao.calculate.core.widget.TitleBarView;
import com.amao.calculate.database.dao.ContactDao;
import com.amao.calculate.database.entity.ContactEntity;
import com.amao.calculate.utils.OptWhat;
import com.amao.calculate.viewholder.ContactViewHolder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * 联系人列表
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/23 16:49
 */
@ContentView(R.layout.act_contact_list)
public class ContactListActivity extends BaseActivity {

    @ViewInject(R.id.text_no_msg)
    private TextView textNoMsg;

    @ViewInject(R.id.recycle_contact)
    private RecyclerView recyclerContact;

    private VRecyclerAdapter<ContactEntity> adapter;
    private List<ContactEntity> contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerHandler();

        contactList = ContactDao.findAllContacts();
        if (contactList == null || contactList.isEmpty()) {
            textNoMsg.setVisibility(View.VISIBLE);
            recyclerContact.setVisibility(View.GONE);
        } else {
            textNoMsg.setVisibility(View.GONE);
            recyclerContact.setVisibility(View.VISIBLE);

            adapter = new VRecyclerAdapter<>(this, contactList);
            adapter.setViewHolder(ContactViewHolder.class);
            recyclerContact.setAdapter(adapter);
            recyclerContact.setLayoutManager(new NotifyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            ((SimpleItemAnimator) recyclerContact.getItemAnimator()).setSupportsChangeAnimations(false);
        }
    }

    @Event(TitleBarView.LEFT_ID)
    private void onClickLeft(View view) {
        finish();
    }

    @SwitchCase(value = OptWhat.SELECT_CONTACT, info = "选择联系人")
    private void selectNum(ContactEntity entity) {
        finish();
    }
}
