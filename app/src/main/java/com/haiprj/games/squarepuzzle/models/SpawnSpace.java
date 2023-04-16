package com.haiprj.games.squarepuzzle.models;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.haiprj.games.squarepuzzle.Const;

public class SpawnSpace extends RectF {

    private Shape shape;
    private final int padding = 0;
    private Paint test;
    private PointF defaultPoint;
    public SpawnSpace(float left, float top) {
        super(
                left,
                top,
                0,
                0);
        this.right = left + Const.squareSize * 3 + padding * 2;
        this.bottom = top + Const.squareSize * 3 + padding * 2;
        shape = createShape();
        test = new Paint();
        test.setStyle(Paint.Style.STROKE);
        test.setColor(Color.WHITE);
    }

    private Shape createShape(){
        float x = this.left + padding;
        float y = this.top + padding;

        Shape s = new Shape(
                this,
                x,
                y
        );
        s.setListener(() -> shape = createShape());
        float sX = s.left;
        float sY = s.top;
        float sWidth = s.width();
        float sHeight = s.height();
        s.left = centerX() - sWidth / 2f;
        s.top = centerY() - sHeight / 2f;
        s.updateSize();
        s.updateChild(s.left - sX, s.top - sY);
        setDefaultPoint(new PointF(s.left, s.top));
        return s;
    }

    public void setDefaultPoint(PointF defaultPoint) {
        this.defaultPoint = defaultPoint;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(this, test);
        shape.draw(canvas);
    }

    public void update() {
        if (!shape.isHover) {
            resetToDefaultPoint();
        }
    }
    public Shape getShape() {
        return shape;
    }

    private void resetToDefaultPoint() {
        float sX = this.shape.left;
        float sY = this.shape.top;
        this.shape.left = defaultPoint.x;
        this.shape.top = defaultPoint.y;
        this.shape.updateSize();
        this.shape.updateChild(this.shape.left - sX, this.shape.top - sY);
    }

}
