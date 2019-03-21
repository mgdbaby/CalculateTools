package com.amao.calculate.dialog;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.Gravity;

import com.amao.calculate.AppConfig;
import com.amao.calculate.R;
import com.amao.calculate.bean.DataBean;
import com.amao.calculate.core.BaseDialog;
import com.amao.calculate.core.SwitchCase;
import com.amao.calculate.core.VRecyclerAdapter;
import com.amao.calculate.utils.OptWhat;
import com.amao.calculate.viewholder.SelectNumViewHolder;
import com.amao.calculate.width.SlideLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择号码的弹框
 * <br/>
 * <li>Author 毛该得
 * <li>Email maogaide@vargo.com.cn
 * <li>Date 2019/3/20 17:06
 */
@ContentView(R.layout.dialog_select_type)
public class SelectNumDialog extends BaseDialog implements SlideLayout.OnSlideListener {

    @ViewInject(R.id.recycler_num)
    private RecyclerView recyclerNum;

    private VRecyclerAdapter<DataBean> adapter;
    private List<DataBean> dataBeans;

    @Override
    public int initStyle() {
        return Gravity.BOTTOM;
    }

    @Override
    protected void initViewData() {
        registerHandler();

        dataBeans = getDataBeans();

        //初始化适配器
        adapter = new VRecyclerAdapter<>(getContext(), dataBeans);
        adapter.setViewHolder(SelectNumViewHolder.class);
        adapter.setOnSlideListener(this);

        //初始化列表控件
        recyclerNum.setLayoutManager(new GridLayoutManager(getContext(), 7));
        recyclerNum.setAdapter(adapter);
        ((SimpleItemAnimator) recyclerNum.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @SwitchCase(value = OptWhat.SELECT_NUM, info = "选择号码")
    private void selectNum(DataBean bean) {
        if(isVisible()) dismiss();
    }

    /**
     * 初始化数据
     */
    private List<DataBean> getDataBeans() {
        List<DataBean> list = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            list.add(new DataBean(i, getNumBgResId(i), AppConfig.OrderType.TYPE_NUM));
        }
        return list;
    }

    /**
     * 获取1~49背景色id
     *
     * @param num 号码
     */
    private static int getNumBgResId(int num) {
        int color;
        if (num == 1 || num == 2 || num == 7 || num == 8 || num == 12 || num == 13 || num == 18 || num == 19 || num == 23 || num == 24 || num == 29 || num == 30 || num == 34 || num == 35 || num == 40 || num == 45 || num == 46) {
            color = R.drawable.num_bg_red;
        } else if (num == 3 || num == 4 || num == 9 || num == 10 || num == 14 || num == 15 || num == 20 || num == 25 || num == 26 || num == 31 || num == 36 || num == 37 || num == 41 || num == 42 || num == 47 || num == 48) {
            color = R.drawable.num_bg_blue;
        } else {
            color = R.drawable.num_bg_green;
        }
        return color;
    }

    @Override
    public void onSlide(boolean isOpen) {

    }
}
