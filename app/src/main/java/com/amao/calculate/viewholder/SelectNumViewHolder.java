package com.amao.calculate.viewholder;

import android.view.View;
import android.widget.TextView;

import com.amao.calculate.R;
import com.amao.calculate.bean.DataBean;
import com.amao.calculate.core.BaseVO;
import com.amao.calculate.core.ListViewHolder;
import com.amao.calculate.utils.MsgUtils;
import com.amao.calculate.utils.OptWhat;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 选择号码的弹框
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/20 17:41
 */
@ContentView(R.layout.item_select_num)
public class SelectNumViewHolder extends ListViewHolder {

    @ViewInject(R.id.text_num)
    private TextView textNum;

    public SelectNumViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public <T extends BaseVO> void loadViewData(int pos, T data) {
        DataBean dataBean = (DataBean) data;
        textNum.setBackground(dataBean.getDrawable());
        textNum.setText(dataBean.getContent());
    }

    @Event(R.id.layout_item)
    private void onClickItem(View view) {
        MsgUtils.send(OptWhat.SELECT_NUM, getItem());
    }
}