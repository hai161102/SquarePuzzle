package com.haiprj.games.squarepuzzle.utils;

import android.graphics.Bitmap;

public class BitmapContainer {
    private static BitmapContainer instance;
    public Bitmap[] squaresBitmap;
    public Bitmap boardBitmap;
    public Bitmap borderBoardBitmap;
    public Bitmap gray;
    private BitmapContainer() {
    }

    public static BitmapContainer getInstance() {
        if (instance == null) instance = new BitmapContainer();
        return instance;
    }
}
