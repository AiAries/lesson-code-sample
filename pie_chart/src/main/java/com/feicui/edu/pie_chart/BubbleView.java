package com.feicui.edu.pie_chart;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Aries on 2018/3/1.
 */

public class BubbleView extends View {

    private final Path path;
    private final Paint paint;
    private final RectF rect;
    int startX  = 70 ,startY = 50;
    int rectW = 100;
    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        rect = new RectF(startX, startY, startX+rectW, startY+rectW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);

        path.moveTo(startX-30,startY);

        path.lineTo((float) (startX+rectW*Math.sin(Math.toRadians(30))/2), (float) (startY
                    +rectW*Math.sin(Math.toRadians(10)/2))
        );

        path.addArc(rect,-120,300);
        path.lineTo(startX-30,startY);
        canvas.drawPath(path,paint);

////设置Paint
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(10f);
////设置Path
//        Path path = new Path();
////屏幕左上角（0,0）到（200,400）画一条直线
//        path.moveTo(200,200);
//        path.lineTo(200, 400);
////(200, 400)到（400,600）画一条直线
//        path.lineTo(400, 600);
////以（400,600）为起始点（0,0）偏移量为（400,600）画一条直线，
////其终点坐标实际在屏幕的位置为（800,1200）
//        path.rLineTo(400, 600);
////        path.addArc();
//        canvas.drawPath(path, paint);

//        paint.setColor(Color.GREEN);
//        canvas.drawArc(rect,90,360,true,paint);

    }
}
