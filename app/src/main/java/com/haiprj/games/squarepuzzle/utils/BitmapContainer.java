package com.haiprj.games.squarepuzzle.utils;

import android.graphics.Bitmap;

public class BitmapContainer {
    private static BitmapContainer instance;
    public Bitmap[] squaresBitmap;
    private BitmapContainer() {
    }

    public static BitmapContainer getInstance() {
        if (instance == null) instance = new BitmapContainer();
        return instance;
    }
}
