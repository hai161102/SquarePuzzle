package com.haiprj.games.squarepuzzle.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.animations.DestroyAnimation;
import com.haiprj.games.squarepuzzle.base.animation.BaseAnimation;

public class Square extends RectF {

    private Bitmap bitmap;
    private Paint test;
    private final DestroyAnimation destroyAnimation;
    private Table table;
    private Point tablePoint;

    public void setTablePoint(Point tablePoint) {
        this.tablePoint = tablePoint;
    }

    public Point getTablePoint() {
        return tablePoint;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Square(Bitmap bitmap, float left, float top) {
        super(left, top, 0, 0);
        this.bitmap = bitmap;
        this.startLeft = this.left;
        this.startTop = this.top;
        init();
        destroyAnimation = new DestroyAnimation(60, new BaseAnimation.AnimationActionListener() {
            @Override
            public void onStart() {
                onStartAnimation();
            }

            @Override
            public void onUpdate() {
                onAnimationRun();
            }

            @Override
            public void onEnd() {
                Square.this.bitmap = null;
                table.clearSquare();
            }
        });
    }

    public void updateSize() {
        resetLocation(this.left, this.top);
    }

    private void init() {
        test = new Paint();
        test.setStyle(Paint.Style.FILL);
        test.setColor(Color.WHITE);
        setBitmap(bitmap);

    }

    public void draw(Canvas canvas) {
        if (bitmap != null)
            canvas.drawBitmap(bitmap, this.left, this.top, null);
        //canvas.drawRect(this, test);
    }
    private void resetLocation(float x, float y) {
        this.left = x;
        this.top = y;
        this.right = left + Const.squareSize;
        this.bottom = top + Const.squareSize;
    }
    public void scale(float scale) {
        this.right = this.left + Const.squareSize * scale;
        this.bottom = this.top + Const.squareSize * scale;
        this.bitmap = Bitmap.createScaledBitmap(this.bitmap, (int) (this.width()), (int) (this.height()), true);
    }

    public void resize(float width, float height) {
        this.right = this.left + width;
        this.bottom = this.top + height;
        this.bitmap = Bitmap.createScaledBitmap(this.bitmap, (int) (width), (int) (height), true);
    }

    public void destroy() {
        destroyAnimation.start();
    }
    float width;
    float height;
    float startTop;
    float startLeft;
    private void onStartAnimation() {
        width = this.width();
        height = this.height();
        this.left += 30;
        this.top -= 30;
    }
    public void onAnimationRun() {
        this.top += 30;
        if (this.top > startTop + height * 2){
            destroyAnimation.end();
        }
    }
//
//    private int col, row;
//    public void destroy(int col, int row) {
//        this.col = col;
//        this.row = row;
//        destroy();
//    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        if (this.bitmap != null) {
            this.top = startTop;
            this.left = startLeft;
            updateSize();
            this.bitmap = Bitmap.createScaledBitmap(this.bitmap, (int) this.width(), (int) this.height(), true);

        }
    }

    public void setNull() {
        destroyAnimation.start();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
