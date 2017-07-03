package com.android.wwh.view.acquaintance.customattr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.android.wwh.view.R;


/**
 * Created by we-win on 2017/3/27.
 */

public class MyTextView extends View {

    private static final String TAG = "MyTextView";
    private static final int[] mAttr = { android.R.attr.textColor, R.attr.text };

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.test);
        String text = typedArray.getString(R.styleable.test_text);
        int textAttr = typedArray.getInteger(R.styleable.test_testAttr, -1);
        Log.i(TAG, "text = " + text + " , textAttr = " + textAttr);
        typedArray.recycle();

        int attributeCount = attrs.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);
            Log.i(TAG, "attrName = " + attrName + " , attrVal = " + attrVal);
        }

        // TypedArray其实是用来简化我们的工作的，
        // 如果使用AttributeSet去获得最终的字符串值，那么需要第一步拿到id，第二步再去解析id。
        // 而TypedArray正是帮我们简化了这个过程。过程入下
        int widthDimensionId =  attrs.getAttributeResourceValue(1, -1);
        Log.e(TAG, "layout_width= "+getResources().getDimension(widthDimensionId));
         **/

        TypedArray typedArray = context.obtainStyledAttributes(attrs, mAttr);
        String textColor = typedArray.getString(0);
        String text = typedArray.getString(1);
        Log.i(TAG, "textColor = " + textColor + " , text = " + text);
        typedArray.recycle();
    }

}
