package com.amao.calculate.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.amao.calculate.R;
import com.amao.calculate.core.BaseActivity;
import com.amao.calculate.core.widget.TitleBarView;
import com.amao.calculate.database.dao.ContactDao;
import com.amao.calculate.database.entity.ContactEntity;
import com.amao.calculate.utils.StringUtils;
import com.amao.calculate.utils.TipUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 新建联系人
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/23 15:39
 */
@ContentView(R.layout.act_contact_new)
public class ContactNewActivity extends BaseActivity {

    @ViewInject(R.id.edit_name)
    private EditText editName;

    @ViewInject(R.id.edit_mobile)
    private EditText editMobile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Event(TitleBarView.LEFT_ID)
    private void onClickLeft(View view) {
        finish();
    }

    @Event(TitleBarView.RIGHT_ID)
    private void onClickRight(View view) {
        String name = editName.getText().toString();
        String mobile = editMobile.getText().toString();

        if (TextUtils.isEmpty(name)) {
            TipUtil.showShort(R.string.text_contact_name_null);
            return;
        }
        mobile = StringUtils.trim(mobile);
        ContactEntity entity = new ContactEntity();
        entity.setName(name);
        entity.setMobile(mobile);
        boolean success = ContactDao.save(entity);
        TipUtil.showShort(success ? R.string.text_save_success : R.string.text_save_fail);
        finish();
    }

    @Event(R.id.img_head)
    private void selectHead(View view) {
        // TODO 选择头像
        TipUtil.showShort("选择头像");
    }
}
