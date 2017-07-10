package com.android.wwh.view.hencoder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.android.wwh.view.R;

/**
 * Created by we-win on 2017/7/10.
 */

public class BaseDrawCustomView extends View {

    public BaseDrawCustomView(Context context) {
        this(context, null);
    }

    public BaseDrawCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseDrawCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Paint paint = new Paint();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // Paint.Style.FILL：填充模式;Paint.Style.STROKE：画线模式;Paint.Style.FILL_AND_STROKE：既画线又填充
        // 把绘制模式改为画线模式。
        paint.setStyle(Paint.Style.STROKE);
        // 设置绘制内容的颜色为红色
        paint.setColor(Color.RED);
        // 设置线条的宽度
        paint.setStrokeWidth(20);
        // 在绘制的时候，往往需要开启抗锯齿来让图形和文字的边缘更加平滑。
        // Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 动态开关抗锯齿。
        paint.setAntiAlias(false);

        // 独有信息都是直接作为参数写进 drawXXX() 方法里的
        // 公有信息是统一放在paint参数里的
        // drawCircle(float cx, float cy, float radius, Paint paint)画圆
        // 在(300,300)的位置上绘制了一个半径为200的圆
        canvas.drawCircle(300, 300, 200, paint);

        // 会在原有的绘制效果上加一层半透明的红色遮罩。
        canvas.drawColor(Color.parseColor("#88880000"));
        // 只是使用方式不同，作用都是一样的。
        //        canvas.drawRGB(100, 200, 100);
        //        canvas.drawARGB(100, 100, 200, 100);
        // 这类颜色填充方法一般用于在绘制之前设置底色，或者在绘制之后为界面设置半透明蒙版。

        // 画矩形
        canvas.drawRect(100, 100, 500, 500, paint);
        // 还有两个重载方法 drawRect(RectF rect, Paint paint)
        // 和 drawRect(Rect rect, Paint paint),让你可以直接填写 RectF 或 Rect 对象来绘制矩形。

        // 画点
        paint.setStrokeWidth(20);
        // ROUND 画出来是圆形的点，SQUARE 或 BUTT 画出来是方形的点。
        // 注：Paint.setStrokeCap(cap) 可以设置点的形状，但这个方法并不是专门用来设置点的形状的，而是一个设置线条端点形状的方法。端点有圆头 (ROUND)、平头 (BUTT) 和方头 (SQUARE) 三种
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawPoint(50, 50, paint);

        // 画多个点
        // 这个数组是点的坐标，每两个成一对
        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        // 绘制四个点：(50, 50) (50, 100) (100, 50) (100, 100)
        canvas.drawPoints(points, 2 /* 跳过两个数，即前两个 0 */,
                4 /* 一共绘制四个点*/, paint);

        // 画椭圆
        canvas.drawOval(50, 50, 350, 200, paint);
        // 还有一个重载方法 drawOval(RectF rect, Paint paint)，让你可以直接填写 RectF 来绘制椭圆

        //  画线
        canvas.drawLine(200, 200, 800, 500, paint);

        //  画线（批量）
        float[] pointLines = {20, 20, 120, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
        canvas.drawLines(pointLines, paint);

        // 画圆角矩形
        canvas.drawRoundRect(100, 100, 500, 300, 50, 50, paint);
        // 还有一个重载方法 drawRoundRect(RectF rect, float rx, float ry, Paint paint)

        // 绘制弧形或扇形
        // 使用一个椭圆来描述弧形的。left, top, right, bottom 描述的是这个弧形所在的椭圆
        // startAngle 是弧形的起始角度，sweepAngle 是弧形划过的角度；useCenter 表示是否连接到圆心
        paint.setStyle(Paint.Style.FILL); // 填充模式
        canvas.drawArc(200, 100, 800, 500, -110, 100, true, paint); // 绘制扇形
        canvas.drawArc(200, 100, 800, 500, 20, 140, false, paint); // 绘制弧形
        paint.setStyle(Paint.Style.STROKE); // 画线模式
        canvas.drawArc(200, 100, 800, 500, 180, 60, false, paint); // 绘制不封口的弧形

        // 画自定义图形
        // 当你要绘制的图形比较特殊，使用前面的那些方法做不到的时候，就可以使用 drawPath() 来绘制
        Path path = new Path(); // 初始化 Path 对象
        // 使用 path 对图形进行描述（这段描述代码不必看懂）
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
        canvas.drawPath(path, paint); // 绘制出 path 描述的图形（心形），大功告成

        // Path 可以描述直线、二次曲线、三次曲线、圆、椭圆、弧形、矩形、圆角矩形。
        // Path 有两类方法，一类是直接描述路径的，另一类是辅助的设置或计算。
        // Path 方法第一类：直接描述路径。
        // 这一类方法还可以细分为两组：添加子图形和画线（直线或曲线）
        // 第一组： addXxx() ——添加子图形

        // 添加圆
        // x, y, radius 这三个参数是圆的基本信息，最后一个参数 dir 是画圆的路径的方向。
        // 顺时针 (CW clockwise) 和逆时针 (CCW counter-clockwise)
        Path path2 = new Path(); // 初始化 Path 对象
        path2.addCircle(700, 700, 200, Path.Direction.CW);
        canvas.drawPath(path2, paint);
        // path.AddCircle(x, y, radius, dir) + canvas.drawPath(path, paint) 这种写法，
        // 和直接使用 canvas.drawCircle(x, y, radius, paint) 的效果是一样的，区别只是它的写法更复杂

        // 第二组：xxxTo() ——画线（直线或曲线）
        // 画直线
        // lineTo(x, y) 的参数是绝对坐标，而 rLineTo(x, y) 的参数是相对当前位置的相对坐标。pass：relatively 相对地
        Path path3 = new Path();
        path3.lineTo(100, 700); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
        path3.rLineTo(100, 0); // 由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线
        canvas.drawPath(path3, paint);

        // 画二次贝塞尔曲线 quadTo/rQuadTo
        //这条二次贝塞尔曲线的起点就是当前位置，而参数中的 x1, y1 和 x2, y2 则分别是控制点和终点的坐标。
        // 和 rLineTo(x, y) 同理，rQuadTo(dx1, dy1, dx2, dy2) 的参数也是相对坐标
        // 贝塞尔曲线：贝塞尔曲线是几何上的一种曲线。它通过起点、控制点和终点来描述一条曲线，主要用于计算机图形学。
        // 总之使用它可以绘制很多圆润又好看的图形

        // 画三次贝塞尔曲线 cubicTo/rCubicTo

        // 移动到目标位置 moveTo/rMoveTo
        // 通过 moveTo(x, y) 或 rMoveTo() 来改变当前位置，从而间接地设置这些方法的起点
        paint.setStyle(Paint.Style.STROKE);
        Path path4 = new Path();
        path4.lineTo(800, 800); // 画斜线
        path4.moveTo(800, 800); // 我移~~
        path4.lineTo(200, 0); // 画竖线
        canvas.drawPath(path4, paint);
        // moveTo(x, y) 虽然不添加图形，但它会设置图形的起点，所以它是非常重要的一个辅助方法

        // 画弧形 arcTo
        // 这个方法和 Canvas.drawArc() 比起来，少了一个参数 useCenter，而多了一个参数 forceMoveTo 。
        // 少了 useCenter ，是因为 arcTo() 只用来画弧形而不画扇形，所以不再需要 useCenter 参数；
        // 而多出来的这个 forceMoveTo 参数的意思是，绘制是要「抬一下笔移动过去」，还是「直接拖着笔过去」，区别在于是否留下移动的痕迹
        paint.setStyle(Paint.Style.STROKE);
        Path path5 = new Path();
        path5.lineTo(800, 100);
        path5.arcTo(800, 100, 900, 900, 0, 180, false);
        canvas.drawPath(path5, paint);

        // 弧形的方法
        // addArc() 只是一个直接使用了 forceMoveTo = true 的简化版 arcTo()
        paint.setStyle(Paint.Style.STROKE);
        Path path6 = new Path();
        path6.lineTo(100, 1000);
        path6.addArc(100, 1100, 800, 800, 0, 270);
        canvas.drawPath(path6, paint);

        // close() 封闭当前子图形
        // 把当前的子图形封闭，即由当前位置向当前子图形的起点绘制一条直线。
        paint.setStyle(Paint.Style.STROKE);
        Path path7 = new Path();
        path7.moveTo(200, 1000);
        path7.lineTo(300, 1000);
        path7.lineTo(250, 1200);
        // close() 和 lineTo(起点坐标) 是完全等价的。
        path7.close();
        canvas.drawPath(path7, paint);// 子图形未封闭

        // Path 方法第二类：辅助的设置或计算
        // 设置填充方式
        // FillType四种:EVEN_ODD、WINDING、INVERSE_EVEN_ODD、INVERSE_WINDING
        // WINDING 是「全填充」，而 EVEN_ODD 是「交叉填充」

        // 画 Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        canvas.drawBitmap(bitmap, 200, 100, paint);

        // 绘制文字
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);
        paint.setColor(Color.parseColor("#00FF00"));
        paint.setTextSize(50);//可以设置文字的大小。
        canvas.drawText("funk off...", 200, 100, paint);
    }


}
