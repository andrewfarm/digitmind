package com.andrewofarm.digitmind;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrewofarm.digitmind.network.Network;
import com.andrewofarm.digitmind.network.NetworkLoader;

public class ScribbleActivity extends AppCompatActivity implements ScribbleListener {

    private ScribbleView scribbleView;
    private ImageView miniView;
    private TextView outputView;
    private ConfidenceBarView confidenceBarView;

    private Bitmap digitBitmap;
    private Canvas digitCanvas;

    private Bitmap smallBitmap;
    private float[][] inputActivations = new float[28*28][1];
    private Network network;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scribble);
        scribbleView = (ScribbleView) findViewById(R.id.scribbleview);
        miniView = (ImageView) findViewById(R.id.miniview);
        outputView = (TextView) findViewById(R.id.outputview);
        confidenceBarView = (ConfidenceBarView) findViewById(R.id.confidencebarview) ;
        scribbleView.setScribbleListener(this);
        scribbleView.post(new Runnable() {
            @Override
            public void run() {
                update();
            }
        });
        digitBitmap = Bitmap.createBitmap(28, 28, Bitmap.Config.ARGB_8888);
        digitCanvas = new Canvas(digitBitmap);
        network = NetworkLoader.loadNetwork(this, R.raw.nn);
        outputView.setText("");
        confidenceBarView.setConfidence(0);
    }

    private void update() {
        smallBitmap = Bitmap.createScaledBitmap(scribbleView.getBitmap(), 28, 28, true);
        digitCanvas.drawBitmap(smallBitmap, 0, 0, null);
        miniView.setImageBitmap(
                Bitmap.createScaledBitmap(digitBitmap, miniView.getWidth(), miniView.getHeight(), false));
    }

    public void clearScribble(View view) {
        scribbleView.clear();
        outputView.setText("");
        confidenceBarView.setConfidence(0);
        update();
    }

    private void recognize() {
        int[] pixels = new int[784];
        smallBitmap.getPixels(pixels, 0, 28, 0, 0, 28, 28);
        for (int i = 0; i < 784; i++) {
            inputActivations[i][0] = 1.0f - (float) Color.red(pixels[i]) / 0xFF;
        }

        float[][] outputActivations = network.feedforward(inputActivations);
        float maxActivation = outputActivations[0][0];
        float sum = 0;
        int maxIndex = 0;
        for (int i = 0; i < outputActivations.length; i++) {
            sum += outputActivations[i][0];
            if (outputActivations[i][0] > maxActivation) {
                maxActivation = outputActivations[i][0];
                maxIndex = i;
            }
        }
        outputView.setText(String.valueOf(maxIndex));
        confidenceBarView.setConfidence(maxActivation / sum * 2 - 1);
    }

    @Override
    public void onUpdate(ScribbleView scribbleView) {
        update();
        recognize();
    }
}
