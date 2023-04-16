package com.haiprj.games.squarepuzzle.models;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.MotionEvent;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.utils.BitmapContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shape extends RectF {

    private final Random random;
    private final int[][] struct;
    private final List<Square> squares = new ArrayList<>();
    private float lastX;
    private float lastY;
    public boolean isHover;
    private boolean isOnTable;
    private ShapeListener listener;
    private SpawnSpace spawnSpace;

    public Shape(SpawnSpace spawnSpace, float left, float top) {
        super(left, top, 0, 0);
        this.spawnSpace = spawnSpace;
        random = new Random();
        int index = random.nextInt(GameMatrix.getInstance().count());
        int numberColor = random.nextInt(BitmapContainer.getInstance().squaresBitmap.length);
        struct = GameMatrix.getInstance().getStruct(index);
        for (int i = 0; i < struct.length; i++) {
            for (int j = 0; j < struct[i].length; j++) {
                if (struct[i][j] == 0) continue;
                Square square = new Square(
                        BitmapContainer.getInstance().squaresBitmap[numberColor],
                        i * Const.squareSize + left,
                        j * Const.squareSize + top
                );
                squares.add(square);
            }
        }
        updateSize();
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
//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawRect(this, paint);
    }

    boolean isTouch = false;
    public void onTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouch = this.contains(x, y);
                isHover = true;
                lastX = x;
                lastY = y;
                break;

            case MotionEvent.ACTION_MOVE:
                if (!isTouch) return;
                isHover = true;
                float deltaX = x - lastX;
                float deltaY = y - lastY;
                lastX = x;
                lastY = y;
                this.left += deltaX;
                this.top += deltaY;

                updateSize();
                updateChild(deltaX, deltaY);
                break;
            case MotionEvent.ACTION_UP:
                isHover = false;
                if (isOnTable) {
                    addToTable();
                    listener.onDestroy();
                }
                break;
        }
    }

    private void addToTable() {

    }

    public boolean isOnTable() {
        return isOnTable;
    }

    public void setOnTable(boolean onTable) {
        isOnTable = onTable;
    }

    public interface ShapeListener {
        void onDestroy();
    }
}
