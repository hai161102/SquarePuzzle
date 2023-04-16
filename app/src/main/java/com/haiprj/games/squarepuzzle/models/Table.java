package com.haiprj.games.squarepuzzle.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.haiprj.games.squarepuzzle.Const;

public class Table extends RectF {

    Paint paint;
    RectF[][] children;
    public Table(float left, float top) {
        super(left, top, 0, 0);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        children = new RectF[Const.TAB_COL][Const.TAB_ROW];
        for (int i = 0; i < Const.TAB_COL; i++) {
            for (int j = 0; j < Const.TAB_ROW; j++) {
                children[i][j] = new RectF(
                        i * Const.squareSize + this.left,
                        j * Const.squareSize + this.top,
                        i * Const.squareSize + Const.squareSize + this.left,
                        j * Const.squareSize + Const.squareSize + this.top
                );
            }
        }
        this.right = this.left + Const.TAB_COL * Const.squareSize;
        this.bottom = this.top + Const.TAB_ROW * Const.squareSize;
    }

    public void draw(Canvas canvas){
        canvas.drawRect(this, paint);
        for (RectF[] child : children) {
            for (RectF rectF : child) {
                canvas.drawRect(rectF, paint);
            }
        }
    }

    public void update(Shape shape) {
        shape.setOnTable(isContainShape(shape));
    }

    private boolean isContainShape(Shape shape) {
        if (
                this.contains(shape.left, shape.top)
                && this.contains(shape.right, shape.top)
                && this.contains(shape.left, shape.bottom)
                && this.contains(shape.right, shape.bottom)
        ){
            previewLocation(shape);
            return true;
        }
        return false;
    }

    public void previewLocation(Shape shape) {

    }
}
