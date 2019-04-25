package com.andrewofarm.digitmind;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Andrew on 8/11/17.
 */

public class ConfidenceBarView extends View {

    private float confidence;

    private Paint paint = new Paint();

    public ConfidenceBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int r, g, b;
        if (confidence < 0.5) {
            r = 255;
            g = (int) (confidence * 510);
        } else {
            r = (int) ((1.0f - confidence) * 510);
            g = 255;
        }
        b = 0;
        paint.setColor(Color.rgb(r, g, b));
        canvas.drawRect(0, (1.0f - confidence) * canvas.getHeight(),
                canvas.getWidth(), canvas.getHeight(), paint);
    }
}
