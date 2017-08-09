package com.andrewofarm.digitmind;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements ScribbleListener {

    private ScribbleView scribbleView;
    private ImageView miniView;

    private Bitmap digitBitmap;
    private Canvas digitCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scribbleView = (ScribbleView) findViewById(R.id.scribbleview);
        miniView = (ImageView) findViewById(R.id.miniview);
        scribbleView.setScribbleListener(this);
        digitBitmap = Bitmap.createBitmap(28, 28, Bitmap.Config.ARGB_8888);
        digitCanvas = new Canvas(digitBitmap);
    }

    private void updateMiniView() {
        digitCanvas.drawBitmap(
                Bitmap.createScaledBitmap(scribbleView.getBitmap(), 28, 28, true), 0, 0, null);
        miniView.setImageBitmap(
                Bitmap.createScaledBitmap(digitBitmap, miniView.getWidth(), miniView.getHeight(), false));
    }

    public void clearScribble(View view) {
        scribbleView.clear();
        updateMiniView();
    }

    @Override
    public void onUpdate(ScribbleView scribbleView) {
        updateMiniView();
    }
}
