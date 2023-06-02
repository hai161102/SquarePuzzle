package com.haiprj.games.squarepuzzle.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.base.utils.GameUtils;
import com.haiprj.games.squarepuzzle.utils.BitmapContainer;

public class SpawnSpace extends RectF {

    private Shape shape;
    private final int padding = 10;
    private PointF defaultPoint;
    private Bitmap borderBitmap;
    private final Shape.ShapeListener spawnShapeListener;
    private final Shape.ShapeListener shapeListener = new Shape.ShapeListener() {

        @Override
        public void onDestroy() {
            shape = null;
            spawnShapeListener.onDestroy();
        }

        @Override
        public void onTouchMove() {
            spawnShapeListener.onTouchMove();
        }

        @Override
        public void onTouchUp() {
            spawnShapeListener.onTouchUp();
        }
    };


    public SpawnSpace(float left, float top, float right, float bottom, Shape.ShapeListener shapeListener) {
        super(left, top, right, bottom);
        this.spawnShapeListener = shapeListener;
        this.bottom = top + Const.squareSize * 3 + padding * 5;
        shape = createShape();
        Paint test = new Paint();
        test.setStyle(Paint.Style.STROKE);
        test.setColor(Color.WHITE);
        this.borderBitmap
                = Bitmap.createScaledBitmap(BitmapContainer.getInstance().borderSpawnBitmap, (int) this.width(), (int) this.height(), true);
    }
    private Shape createShape(){
        float x = this.left + padding;
        float y = this.top + padding;

        Shape s = new Shape(x, y);
        s.setListener(shapeListener);
        //s.scaleSize(0.5f);
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

    public void setFlipBitmap() {
        this.borderBitmap = GameUtils.createFlippedBitmap(this.borderBitmap, false, true);
    }

    public void setDefaultPoint(PointF defaultPoint) {
        this.defaultPoint = defaultPoint;
    }
    Canvas canvas;
    public void draw(Canvas canvas) {
        this.canvas = canvas;
//        this.canvas.drawRect(this, test);
        Bitmap b = borderBitmap;
        float borderX = this.centerX() - b.getWidth() / 2f;
        float borderY = this.centerY() - b.getHeight() / 2f;
        this.canvas.drawBitmap(b, borderX, borderY, null);
        shape.draw(this.canvas);
    }

    public void update() {
    }
    public Shape getShape() {
        return shape;
    }

    public void resetToDefaultPoint() {
        float sX = this.shape.left;
        float sY = this.shape.top;
        this.shape.left = defaultPoint.x;
        this.shape.top = defaultPoint.y;
        this.shape.updateSize();
        this.shape.updateChild(this.shape.left - sX, this.shape.top - sY);
    }

    public void addNewShape() {
        shape = createShape();
    }
}
