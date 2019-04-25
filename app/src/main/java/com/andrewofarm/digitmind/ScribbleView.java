package com.andrewofarm.digitmind;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Andrew on 8/8/17.
 */

public class ScribbleView extends View {

    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private float lastX, lastY;

    private ScribbleListener scribbleListener;

    public ScribbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStrokeWidth(80);
        paint.setColor(Color.BLACK);
        paint.setStrokeCap(Paint.Cap.ROUND);
        post(new Runnable() {
            @Override
            public void run() {
                bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                canvas = new Canvas(bitmap);
                clear();
            }
        });
    }

    public void setScribbleListener(ScribbleListener scribbleListener) {
        this.scribbleListener = scribbleListener;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    private void update() {
        scribbleListener.onUpdate(this);
        invalidate();
    }

    public void clear() {
        canvas.drawColor(Color.WHITE);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (isInEditMode()) return;
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float thisX = event.getX();
                float thisY = event.getY();
                canvas.drawLine(lastX, lastY, thisX, thisY, paint);
                lastX = thisX;
                lastY = thisY;
                update();
                break;
        }
        return true;
    }


}
