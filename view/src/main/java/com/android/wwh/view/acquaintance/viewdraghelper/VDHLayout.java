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

public class VDHLayout extends LinearLayout {

    private final ViewDragHelper mViewDragHelper;

    public VDHLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 第二个参数sensitivity，主要用于设置touchSlop
        // helper.mTouchSlop = (int) (helper.mTouchSlop * (1 / sensitivity));
        // 传入越大，mTouchSlop的值就会越小
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            /**
             * 如果返回ture则表示可以捕获该view，你可以根据传入的第一个view参数决定哪些可以捕获
             * @param child
             * @param pointerId
             * @return
             */
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;

                //newLeft ->  left为即将移动到的位置，需要大于leftBound小于rightBound
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);

                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                return top;
            }
        });
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
