package com.samskrut.vrvideo;

import android.os.Bundle;

import com.google.vrtoolkit.cardboard.CardboardActivity;

import org.rajawali3d.cardboard.RajawaliCardboardRenderer;
import org.rajawali3d.cardboard.RajawaliCardboardView;


public class MainActivity extends CardboardActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RajawaliCardboardView view = new RajawaliCardboardView(MainActivity.this);
        setContentView(view);
        setCardboardView(view);
        RajawaliCardboardRenderer renderer = new VideoRenderer(MainActivity.this, getIntent().getExtras().getString("fpath"));
        view.setRenderer(renderer);
        view.setSurfaceRenderer(renderer);

    }


}