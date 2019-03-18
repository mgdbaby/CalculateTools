package com.amao.calculate.width;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.amao.calculate.R;

import java.lang.ref.WeakReference;



public class SlideLayout extends LinearLayout {

    private int width;
//    private int height;

    private Scroller scroller;
//    private int leftBorder;
    private int rightBorder;
    private int touchSlop;
    private int slideSlop;
    private int duration;
    private float dX, dY;//TouchEvent_ACTION_DOWN坐标(dX,dY)
    private float lastX;//TouchEvent最后一次坐标(lastX,lastY)
    private boolean isMoveValid;
    private boolean isOpen;
    private OnSlideListener listener;
    private boolean canSlide = true;

    private static WeakReference<SlideLayout> openLayout;

    public SlideLayout(Context context) {
        this(context, null);
    }

    public SlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTypedArray(context, attrs);
        init(context);
    }

    private void initTypedArray(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlideLayout);
        float defSlideSlop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        this.slideSlop = (int) typedArray.getDimension(R.styleable.SlideLayout_slideSlop, defSlideSlop);
        this.duration = typedArray.getInteger(R.styleable.SlideLayout_duration, 250);
        typedArray.recycle();
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        scroller = new Scroller(context);
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if(changed && getChildCount() > 0){
            width = getChildAt(0).getWidth();
            rightBorder = getChildAt(getChildCount() - 1).getRight();
        }
    }

    /**
     * 关闭打开的Layout
     */
    private boolean closeOpenLayout(float x){
        SlideLayout oldLayout = (openLayout == null ? null : openLayout.get());
        // 不是同一行，或者当前行的内容区, 点击则关闭
        if(oldLayout != null && (oldLayout != this || x < (width - (rightBorder - width)))){
            oldLayout.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (closeOpenLayout(ev.getRawX())) {
                return false;
            } else {
                final float eX = ev.getRawX();
                final float eY = ev.getRawY();
                lastX = dX = eX;
                dY = eY;
                super.dispatchTouchEvent(ev);
                return true;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            final float eX = ev.getRawX();
            final float eY = ev.getRawY();
            //当横向ACTION_MOVE值大于TouchSlop时，拦截子控件的事件
            if (Math.abs(eX - dX) > touchSlop && Math.abs(eX - dX) > Math.abs(eY - dY)) {
                return true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_UP){
            closeAllItem();
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float eX = event.getRawX();
        final float eY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!canSlide) {
                    return false; // create by damao  2018/3/28 18:24  不处理move事件
                }

                if (!isMoveValid && Math.abs(eX - dX) > touchSlop && Math.abs(eX - dX) > Math.abs(eY - dY)) {
                    //禁止父控件拦截事件
                    requestDisallowInterceptTouchEvent(true);
                    isMoveValid = true;
                }
                if (isMoveValid) {
                    int offset = (int) (lastX - eX);
                    lastX = eX;
                    if (getScrollX() + offset < 0) {
                        toggle(false, false);
                        dX = eX;//reset eX
                    } else if (getScrollX() + offset > rightBorder - width) {
                        toggle(true, false);
                        dX = eX;//reset eX
                    } else {
                        scrollBy(offset, 0);
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (isMoveValid) {
                    if (eX - dX < -slideSlop) {
                        toggle(true, true);
                    } else if (eX - dX > slideSlop) {
                        toggle(false, true);
                    } else {
                        toggle(isOpen, true);
                    }
                    isMoveValid = false;
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public static void closeAllItem(){
        SlideLayout oldLayout;
        if(openLayout != null && (oldLayout = openLayout.get()) != null){
            oldLayout.close();
            openLayout.clear();
        }
        openLayout = null;
    }

    private void toggle(boolean open, boolean withAnim) {
        if (isOpen == open) {
            return;
        }

        if(listener != null){
            listener.onSlide(open);
        }

        isOpen = open;
        if (isOpen) {
            // 缓存打开的Layout
            openLayout = new WeakReference<>(this);

            if (withAnim) {
                smoothScrollTo(rightBorder - width, duration);
            } else {
                scrollTo(rightBorder - width, 0);
            }
        } else {
            if(openLayout != null){
                openLayout.clear();
            }
            if (withAnim) {
                smoothScrollTo(0, duration);
            } else {
                scrollTo(0, 0);
            }
        }
    }

    private void smoothScrollTo(int dstX, int duration) {
        int offset = dstX - getScrollX();
        scroller.startScroll(getScrollX(), 0, offset, 0, duration);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }


    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open, boolean withAnim) {
        toggle(open, withAnim);
    }

    public void open() {
        toggle(true, true);
    }

    public void close() {
        toggle(false, true);
    }

    public boolean isCanSlide() {
        return canSlide;
    }

    public void setCanSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }

    public interface OnSlideListener {
        void onSlide(boolean isOpen);
    }

    public void setOnSlideListener(OnSlideListener listener) {
        this.listener = listener;
    }
}
