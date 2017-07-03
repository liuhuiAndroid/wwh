package com.android.wwh.view.acquaintance.viewdraghelper;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by we-win on 2017/3/27.
 */

public class VDHLayout2 extends LinearLayout {

    private final ViewDragHelper mViewDragHelper;
    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;
    private int mAutoBackOriginPosX;
    private int mAutoBackOriginPosY;

    public VDHLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView || child == mAutoBackView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                return left;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }

            //手指释放的时候回调
            //因为其内部使用的是mScroller.startScroll，所以别忘了需要invalidate()以及结合computeScroll方法一起？？？
            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
//                super.onViewReleased(releasedChild, xvel, yvel);
                if(releasedChild == mAutoBackView){
                    mViewDragHelper.settleCapturedViewAt(mAutoBackOriginPosX,mAutoBackOriginPosY);
                    invalidate();
                }
            }

            //在边界拖动时回调
            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                mViewDragHelper.captureChildView(mEdgeTrackerView,pointerId);
            }

            //如果子View不消耗事件，下面两个方法返回大于0的值才能正常的捕获
            //方法的返回值应当是该childView横向或者纵向的移动的范围，当前如果只需要一个方向移动，可以只复写一个。
            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }
        });

        //使用边界检测需要
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //①
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 主要功能是计算拖动的位移量、更新背景、设置要显示的屏幕
     * 重写computeScroll()的原因:调用startScroll()是不会有滚动效果的，只有在computeScroll()获取滚动情况，做出滚动的响应
     * computeScroll在父控件执行drawChild时，会调用这个方法
     *
     *
     */
    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            // invalidate()是用来刷新View的，必须是在UI线程中进行工作。
            // 比如在修改某个view的显示时，调用invalidate()才能看到重新绘制的界面。
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mAutoBackOriginPosX = mAutoBackView.getLeft();
        mAutoBackOriginPosY = mAutoBackView.getRight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

}
