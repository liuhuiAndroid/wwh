package com.android.wwh.view.acquaintance.second;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.android.wwh.view.R;

/**
 * Created by we-win on 2017/3/24.
 */

public class CustomImageView extends View {

    private static final int IMAGE_SCALE_FITXY = 0;
    private static final int IMAGE_SCALE_CENTER = 0;

    private String mTitleText;
    private int mTitileTextColor;
    private int mTitleTextSize;
    private Bitmap mImage;
    private int mImageScale;
    private Rect mRect;
    private Paint mPaint;
    private Rect mTextBound;
    private int mWidth;
    private int mHeight;

    public CustomImageView(Context context) {
        this(context,null);
    }

    public CustomImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyleAttr, 0);
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int index = typedArray.getIndex(i);
            switch (index){
                case R.styleable.CustomImageView_image:
                    int resourceId = typedArray.getResourceId(index, 0);
                    mImage = BitmapFactory.decodeResource(getResources(), resourceId);
                    break;
                case R.styleable.CustomImageView_imageScaleType:
                    mImageScale = typedArray.getInt(index, 0);
                    break;
                case R.styleable.CustomImageView_titleText:
                    mTitleText = typedArray.getString(index);
                    break;
                case R.styleable.CustomImageView_titleTextColor:
                    // 默认颜色设置为黑色
                    mTitileTextColor = typedArray.getColor(index, Color.BLACK);
                    break;
                case R.styleable.CustomImageView_titleTextSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    mTitleTextSize = typedArray.getDimensionPixelSize(index, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
            }
        }

        typedArray.recycle();
        mRect = new Rect();
        mPaint = new Paint();
        mTextBound = new Rect();

        mPaint.setTextSize(mTitleTextSize);
        mPaint.getTextBounds(mTitleText,0,mTitleText.length(),mTextBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY){
            mWidth = widthSize;
        }else{
            // 由图片决定的宽
            int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
            // 由字体决定的宽
            int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

            int desire = Math.max(desireByImg,desireByTitle);
            mWidth = Math.min(desire,widthSize);
        }

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if(heightMode == MeasureSpec.EXACTLY){
            mHeight = heightSize;
        }else{
            int desire = getPaddingTop()+getPaddingBottom()+mImage.getHeight()+mTextBound.height();
            mHeight = Math.min(desire,heightSize);
        }

        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        /**
         * 边框
         */
        //设置线宽
        mPaint.setStrokeWidth(10);
        //设置为空心
        mPaint.setStyle(Paint.Style.STROKE);
        //青色
        mPaint.setColor(Color.CYAN);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mRect.left = getPaddingLeft();
        mRect.right = mWidth - getPaddingRight();
        mRect.top = getPaddingTop();
        mRect.bottom = mHeight - getPaddingBottom();

        mPaint.setColor(mTitileTextColor);
        mPaint.setStyle(Paint.Style.FILL);
        /**
         * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
         */
        if (mTextBound.width() > mWidth)
        {
            TextPaint paint = new TextPaint(mPaint);
            String msg = TextUtils.ellipsize(mTitleText, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(),
                    TextUtils.TruncateAt.END).toString();
            canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);
        } else {
            //正常情况，将字体居中
            canvas.drawText(mTitleText, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
        }

        //取消使用掉的快
        mRect.bottom -= mTextBound.height();

        if (mImageScale == IMAGE_SCALE_FITXY)
        {
            canvas.drawBitmap(mImage, null, mRect, mPaint);
        } else
        {
            //计算居中的矩形范围
            mRect.left = mWidth / 2 - mImage.getWidth() / 2;
            mRect.right = mWidth / 2 + mImage.getWidth() / 2;
            mRect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
            mRect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;

            canvas.drawBitmap(mImage, null, mRect, mPaint);
        }
    }
}
