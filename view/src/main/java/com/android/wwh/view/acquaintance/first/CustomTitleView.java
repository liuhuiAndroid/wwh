package com.android.wwh.view.acquaintance.first;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.android.wwh.view.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * Created by we-win on 2017/3/24.
 */

public class CustomTitleView extends View {

    private static final String TAG = "CustomTitleView";
    private int mTitleTextSize;
    private int mTitileTextColor;
    private String mTitleText;
    private Paint mPaint;
    private Rect mRect;

    public CustomTitleView(Context context) {
        this(context,null);
    }

    /**
     * 默认的布局文件调用的是两个参数的构造方法
     */
    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         * 获得我们所定义的自定义样式属性
         */
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.CustomTitleView_titleText:
                    mTitleText = typedArray.getString(index);
                    break;
                case R.styleable.CustomTitleView_titleTextColor:
                    // 默认颜色设置为黑色
                    mTitileTextColor = typedArray.getColor(index, Color.BLACK);
                    break;
                case R.styleable.CustomTitleView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTitleTextSize);
        mRect = new Rect();
        //由调用者返回在边界(分配)的最小矩形包含所有的字符
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(), mRect);

        //点击事件
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mTitleText = randomText();
                // invalidate:使无效; 使作废; 证明…错误; 使站不住脚
                // invalidate和postInvalidate都是用来重绘View搜索，
                // 区别就是invalidate只能在主线程中调用，postInvalidate可以在子线程中调用 requestLayout则是请求重新布局View
                postInvalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width,height;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else{
            int textWidth = mRect.width();
            width = getPaddingLeft() + getPaddingRight() + textWidth;
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else{
            int textHeight = mRect.height();
            height = getPaddingTop() + getPaddingBottom() + textHeight;
        }

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
                            //600 300
                            //259  88
        mPaint.setColor(mTitileTextColor);

        /**
         * 参考http://blog.csdn.net/lovexieyuan520/article/details/43153275
         * canvas.drawText(text, x, y, paint)
         * x默认是这个字符串的左边在屏幕的位置，如果设置了paint.setTextAlign(Paint.Align.CENTER);那就是字符的中心
         * y是指定这个字符baseline在屏幕上的位置
         */
        canvas.drawText(mTitleText,getWidth()/2 - mRect.width()/2,
                getHeight()/2 + mRect.height()/2 ,mPaint);
        Log.i(TAG,"getWidth()/2 = "+getWidth()/2);
        Log.i(TAG,"mRect.width()/2 = "+mRect.width()/2);
        Log.i(TAG,"getHeight()/2 = "+getHeight()/2);
        Log.i(TAG,"mRect.height()/2 = "+mRect.height()/2);

    }

    private String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }

        return sb.toString();
    }
}
