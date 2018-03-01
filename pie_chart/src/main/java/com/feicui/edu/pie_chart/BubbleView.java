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

    public BubbleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paint = new Paint();
        rect = new RectF(100,10,200,110);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.moveTo(10,10);

        path.lineTo(100,10);
        paint.setColor(Color.RED);
        canvas.drawPath(path,paint);


        paint.setColor(Color.GREEN);
        canvas.drawArc(rect,90,180,true,paint);

    }
}
