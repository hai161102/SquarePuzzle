package com.haiprj.games.squarepuzzle.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.haiprj.games.squarepuzzle.Const;

public class Square extends RectF {

    private Bitmap bitmap;
    private Paint test;

    public Square(Bitmap bitmap, float left, float top) {
        super(left, top, 0, 0);
        this.bitmap = bitmap;
        updateSize();
        init();
    }

    public void updateSize() {
        resetLocation(this.left, this.top);
    }

    private void init() {
        test = new Paint();
        test.setStyle(Paint.Style.FILL);
        test.setColor(Color.WHITE);
        bitmap = Bitmap.createScaledBitmap(bitmap, (int) this.width(), (int) this.height(), true);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, this.left, this.top, null);
        //canvas.drawRect(this, test);
    }
    private void resetLocation(float x, float y) {
        this.left = x;
        this.top = y;
        this.right = left + Const.squareSize;
        this.bottom = top + Const.squareSize;
    }
}
