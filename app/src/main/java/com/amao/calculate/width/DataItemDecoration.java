package com.amao.calculate.width;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.amao.calculate.R;

public class DataItemDecoration extends RecyclerView.ItemDecoration {
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //先初始化一个Paint来简单指定一下Canvas的颜色
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStrokeWidth(0.4f);
        paint.setColor(ContextCompat.getColor(parent.getContext(), R.color.colorPrimaryDark));

        //获取RecyclerView中的总条目数量
        int childCount = parent.getChildCount();

        //遍历一下
        for (int i = 0; i < childCount; i++) {
            //获得子view，也就是一个条目的view， 准备给他画上边框
            View childView = parent.getChildAt(i);

            //先获得子view的长宽，以及在屏幕上的位置，方便我们得到边框的具体坐标
            float x = childView.getX();
            float y = childView.getY();
            int width = childView.getWidth();
            int height = childView.getHeight();

            //根据这些点画条目的四周的线
//            c.drawLine(x, y, x + width, y, paint);
//            c.drawLine(x, y, x, y + height, paint);
            c.drawLine(x + width, y, x + width, y + height, paint);
            c.drawLine(x, y + height, x + width, y + height, paint);
        }

        super.onDraw(c, parent, state);
    }
}