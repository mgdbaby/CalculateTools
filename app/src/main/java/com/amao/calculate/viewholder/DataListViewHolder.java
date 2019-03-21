package com.amao.calculate.viewholder;

import android.view.View;
import android.widget.TextView;

import com.amao.calculate.R;
import com.amao.calculate.bean.DataBean;
import com.amao.calculate.core.BaseVO;
import com.amao.calculate.core.ListViewHolder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.item_entry_delete)
public class DataListViewHolder extends ListViewHolder {

    @ViewInject(R.id.text_data)
    private TextView textData;

    @ViewInject(R.id.text_money)
    private TextView textMoney;

    public DataListViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public <T extends BaseVO> void loadViewData(int pos, T data) {
        DataBean dataBean = (DataBean) data;
        textData.setText(dataBean.getContent());
        textMoney.setText(dataBean.getMoneyStr());
    }

    @Override
    public boolean canSlide() {
        return true;
    }
}