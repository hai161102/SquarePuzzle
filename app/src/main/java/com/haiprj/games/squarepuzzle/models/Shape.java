package com.haiprj.games.squarepuzzle.models;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.utils.BitmapContainer;
import com.haiprj.games.squarepuzzle.utils.GameRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shape extends RectF {

    private final int[][] struct;
    private final List<Square> squares = new ArrayList<>();
    private final int indexImage;
    private float lastX;
    private float lastY;
    private ShapeListener listener;
    private int numberColor;
    private float width, height;

    public Shape(float left, float top) {
        super(left, top, 0, 0);
        setSize(1);
        indexImage = GameRandom.getInstance().nextInt();
        Random random = new Random();
        numberColor = random.nextInt(BitmapContainer.getInstance().squaresBitmap.length);
        struct = GameMatrix.getInstance().getStruct(indexImage);
        updateView();
        updateSize();
    }

    public Shape(Shape shape) {
        super(shape.left, shape.top, 0, 0);
        setSize(1);
        indexImage = shape.indexImage;
        struct = shape.getStruct();
        updateViewGray();
        updateSize();
    }
    private void updateView() {
        squares.clear();
        int x = 0;
        int y = 0;
        while (x < struct.length && y < struct[x].length) {
            if (struct[x][y] == 1)
            {
                Square square = new Square(
                        BitmapContainer.getInstance().squaresBitmap[numberColor],
                        x * this.width + left,
                        y * this.height + top
                );
                square.resize(this.width, this.height);
                squares.add(square);

            }
            x++;
            if (x >= struct.length) {
                x = 0;
                y++;
            }
        }
    }

    private void updateViewGray() {
        squares.clear();
        int x = 0;
        int y = 0;
        while (x < struct.length && y < struct[x].length) {
            if (struct[x][y] == 1)
            {
                Square square = new Square(
                        BitmapContainer.getInstance().gray,
                        x * this.width + left,
                        y * this.height + top
                );
                square.resize(this.width, this.height);
                squares.add(square);

            }
            x++;
            if (x >= struct.length) {
                x = 0;
                y++;
            }
        }
    }

    public void setPoint(PointF point) {
        this.left = point.x;
        this.top = point.y;
        updateViewGray();
        updateSize();
    }
    private void setSize(float scale) {
        width = Const.squareSize * scale;
        height = Const.squareSize * scale;
    }
    public void updateSize() {
        this.right = struct.length * Const.squareSize + left;
        this.bottom = struct[0].length * Const.squareSize + top;
    }
    public void updateChild(float changeX, float changeY) {
        for (Square square : squares) {
            square.left += changeX;
            square.top += changeY;
            square.updateSize();
        }
    }
    public int[][] getStruct() {
        return struct;
    }

    public void setListener(ShapeListener listener) {
        this.listener = listener;
    }

    public void draw(Canvas canvas) {
        for (Square square : squares) {
            square.draw(canvas);
        }
    }

    public int getIndexImage() {
        return indexImage;
    }

    boolean isTouch = false;
    public void onTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = this.contains(x, y);
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isTouch) return;
                listener.onTouchMove();
                float deltaX = x - lastX;
                float deltaY = y - lastY;
                lastX = x;
                lastY = y;
                this.left += deltaX;
                this.top += deltaY;
                //scaleSize(1);
                updateSize();
                updateChild(deltaX, deltaY);
                break;
            case MotionEvent.ACTION_UP:
                if (isTouch){
                    listener.onTouchUp();
                    isTouch = false;
                }
                break;
        }
    }

    public boolean isTouch() {
        return isTouch;
    }


    public int getNumberColor() {
        return numberColor;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public void destroy() {
        listener.onDestroy();
    }

    public interface ShapeListener {
        void onDestroy();

        void onTouchMove();

        void onTouchUp();
    }
}
