package com.feicui.edu.pie_chart;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/8/22 0022.
 */
public class PieChartView extends View {

    private float startAngle;//起始绘制起点的角度
    private float sweepAngle;//扫描的角度，绘制多大

    private Paint paint;
    private RectF rectF;
    private int color;

    //被代码直接new 来创造对象
    public PieChartView(Context context) {
        super(context);
    }
    //当这个对象被初始化时调用，在xml布局使用时，才会调用这个构造方法
    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义的属性
        TypedArray a = context.obtainStyledAttributes(
                attrs,
                R.styleable.PieChartView,
                0, 0);
        startAngle =  a.getFloat(R.styleable.PieChartView_startAngle, 0);
        sweepAngle  = a.getFloat(R.styleable.PieChartView_sweepAngle, 360);
        color = a.getColor(R.styleable.PieChartView_paintColor, Color.RED);

        initPaint();
        rectF = new RectF(0,0,200,200);
        a.recycle();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(color);
        //消除锯齿
        paint.setAntiAlias(true);

    }

    public void setAngle(final float startAngle, final float sweepAngle)
    {
        this.startAngle = startAngle;
        //每隔一段时间干一件事情
        final Timer timer = new Timer();

        TimerTask task = new TimerTask() {

                @Override
                public void run() {
                    //Toast.makeText(PieChartView.this.getContext(), ""+startA, Toast.LENGTH_SHORT).show();
                    if (PieChartView.this.startAngle + PieChartView.this.sweepAngle >= sweepAngle) {
                        PieChartView.this.sweepAngle = sweepAngle - PieChartView.this.sweepAngle;
                    }
                    PieChartView.this.sweepAngle += 10;
                    //重绘
                    postInvalidate();//在子线程中完成
                    if (PieChartView.this.sweepAngle == sweepAngle) {
                        //当开始的角度等于总的扫描角度，取消任务
                        timer.cancel();
                        PieChartView.this.startAngle = 0;
                    }
        }
    };
        timer.schedule(task,100,100);
//        invalidate();//在主线中完成绘制
    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(100,100,50,paint);
//        canvas.drawCircle(200,200,50,paint);

        //绘制圆弧
        canvas.drawArc(
                rectF,//限定绘制区域
                startAngle,//绘制圆弧的起始角度
                sweepAngle,//圆弧扫描的角度
                true,//是否在矩形中心绘画
                paint//画笔
        );

    }
}
