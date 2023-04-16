package com.haiprj.games.squarepuzzle;

import android.graphics.Bitmap;

public interface OnLoadListener {
        void onLoad();
        void onDone(Bitmap[] bitmaps);
    }