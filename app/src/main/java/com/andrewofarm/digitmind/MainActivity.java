package com.andrewofarm.digitmind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ScribbleView scribbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scribbleView = (ScribbleView) findViewById(R.id.scribbleview);
    }

    public void clearScribble(View view) {
        scribbleView.clear();
    }
}
