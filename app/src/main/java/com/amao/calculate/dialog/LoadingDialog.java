package com.amao.calculate.dialog;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.amao.calculate.R;
import com.amao.calculate.core.BaseDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 通用接口请求加载效果--菊花转.
 */
@ContentView(R.layout.dialog_loading)
public class LoadingDialog extends BaseDialog {

    @ViewInject(R.id.img_loading)
    private ImageView img_loading;

    private AnimationDrawable anim;

    @Override
    protected void initViewData() {
        img_loading.setImageResource(R.drawable.xml_loading);
        anim = (AnimationDrawable) img_loading.getDrawable();
        anim.start();

    }

    @Override
    public void show() {
        super.show();
        if (anim != null) {
            x.task().autoPost(new Runnable() {
                @Override
                public void run() {
                    anim.start();
                }
            });
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (anim != null) {
            x.task().autoPost(new Runnable() {
                @Override
                public void run() {
                    anim.stop();
                }
            });
        }
    }
}
