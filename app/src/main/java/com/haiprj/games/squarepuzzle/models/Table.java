package com.haiprj.games.squarepuzzle.models;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import androidx.annotation.Nullable;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.interfaces.TableListener;
import com.haiprj.games.squarepuzzle.utils.BitmapContainer;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class Table extends RectF {

    Paint paint;
    RectF[][] children;
    private PointF previewPoint;
    private final int[][] mapMatrix = GameMatrix.getInstance().TABLE;
    ShapePreview path;
    PointF[][] points = new PointF[GameMatrix.getInstance().TABLE.length][GameMatrix.getInstance().TABLE[0].length];
    private final List<Square> listSquare = new ArrayList<>();
    private final Square[][] allSquares = new Square[Const.TAB_COL][Const.TAB_ROW];
    private Shape grayShape;
    private TableListener listener;
    private int borderW;
    private int borderH;
    private TableMatrix[][] tableMatrix;

    public Table(float left, float top) {
        super(left, top, 0, 0);
        init();
    }

    public void setListener(TableListener listener) {
        this.listener = listener;
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        children = new RectF[Const.TAB_COL][Const.TAB_ROW];
        tableMatrix = new TableMatrix[Const.TAB_COL][Const.TAB_ROW];
        int i = 0;
        int j = 0;
        while (i < tableMatrix.length && j < tableMatrix[i].length) {
            tableMatrix[i][j] = new TableMatrix();
            tableMatrix[i][j].cellSquare = new Square(
                    null,
                    this.left + i * Const.squareSize,
                    this.top + j * Const.squareSize
            );
            tableMatrix[i][j].cellSquare.setTable(this);
            tableMatrix[i][j].cellSquare.setTablePoint(new Point(i, j));
            i++;
            if (i >= tableMatrix.length) {
                i = 0;
                j++;
            }
        }
        i = 0;
        j = 0;
        while (i < Const.TAB_COL && j < Const.TAB_ROW) {
            children[i][j] = new RectF(
                    i * Const.squareSize + this.left,
                    j * Const.squareSize + this.top,
                    (i + 1) * Const.squareSize + this.left,
                    (j + 1) * Const.squareSize + this.top
            );
            i++;
            if (i >= Const.TAB_COL) {
                i = 0;
                j++;
            }
        }
        this.right = this.left + Const.TAB_COL * Const.squareSize;
        this.bottom = this.top + Const.TAB_ROW * Const.squareSize;
        i = 0;
        j = 0;
        while (i < points.length && j < points[i].length) {
            points[i][j] = new PointF(i * Const.squareSize + this.left, j * Const.squareSize + this.top);
            i++;
            if (i >= points.length) {
                i = 0;
                j++;
            }
        }

        BitmapContainer.getInstance().boardBitmap = Bitmap.createScaledBitmap(
                BitmapContainer.getInstance().boardBitmap,
                (int) this.width(),
                (int) this.height(),
                true
        );
        borderW = (int) (this.width() * 140 / 128f);
        borderH = (int) (this.height() * 140 / 128f);
        BitmapContainer.getInstance().borderBoardBitmap = Bitmap.createScaledBitmap(
                BitmapContainer.getInstance().borderBoardBitmap,
                borderW,
                borderH,
                true
        );
    }


    public void draw(Canvas canvas) {

        canvas.drawBitmap(BitmapContainer.getInstance().boardBitmap,
                this.left,
                this.top,
                null);
        if (grayShape != null && isContainShape(grayShape)) {
            grayShape.draw(canvas);
        }
        canvas.drawBitmap(BitmapContainer.getInstance().borderBoardBitmap,
                this.left - (borderW - this.width()) / 2f,
                this.top - (borderH - this.height()) / 2f,
                null);
//        try {
//            for (Square square : listSquare) {
//                square.draw(canvas);
//            }
//        } catch (Exception e) {
//            draw(canvas);
//        }
        int i = 0;
        int j = 0;
        while (i < tableMatrix.length && j < tableMatrix[i].length) {
            if (tableMatrix[i][j].cellSquare != null){
                tableMatrix[i][j].cellSquare.draw(canvas);
            }
            i++;
            if (i >= tableMatrix.length) {
                i = 0;
                j++;
            }
        }
    }

    public void update() {
        //checkTable();
    }
    private boolean isInCell(Square square) {
        Number numberTop = (square.top - this.top) / Const.squareSize;
        Number numberLeft = (square.left - this.left) / Const.squareSize;
        return isFloat(numberTop) && isFloat(numberLeft);
    }

    private boolean isFloat(Number number) {
        return number instanceof Float;
    }

    private boolean isFullCol(int col) {
        for (int i = 0; i < Const.TAB_ROW; i++) {
            if (mapMatrix[col][i] != 1) return false;
        }
        return true;
    }

    private boolean isFullRow(int row) {
        for (int i = 0; i < Const.TAB_COL; i++) {
            if (mapMatrix[i][row] != 1) return false;
        }
        return true;
    }

    public void update(Shape shape) {
        if (isContainShape(shape)) {
            shape.setOnTable(true);
            previewLocation(shape);
            grayShape = new Shape(shape);
            grayShape.setPoint(previewPoint);
            if (!shape.isTouch()) {
                path = null;
                grayShape = null;
            }

        } else {
            shape.setOnTable(false);
            path = null;
            grayShape = null;
        }
    }

    private boolean isContainShape(Shape shape) {
        return this.contains(shape.left + shape.width() / 4, shape.top + shape.height() / 4)
                && this.contains(shape.right - shape.width() / 4, shape.top + shape.height() / 4)
                && this.contains(shape.left + shape.width() / 4, shape.bottom - shape.height() / 4)
                && this.contains(shape.right - shape.width() / 4, shape.bottom - shape.height() / 4);
    }


    public void previewLocation(Shape shape) {
        path = drawPath(shape);
        previewPoint = nearestMatrix(shape);
        Matrix matrix = new Matrix();
        matrix.postTranslate(previewPoint.x, previewPoint.y);
        path.transform(matrix);
    }


    private RectF getRealShapeRect(Shape shape) {
        float minLeft;
        float minTop;
        float maxLeft;
        float maxTop;
        minLeft = shape.getSquares().get(0).left;
        minTop = shape.getSquares().get(0).top;
        maxLeft = shape.getSquares().get(shape.getSquares().size() - 1).left;
        maxTop = shape.getSquares().get(shape.getSquares().size() - 1).top;
        for (RectF rectF : shape.getSquares()) {
            if (minLeft > rectF.left) {
                minLeft = rectF.left;
            }
            if (minTop > rectF.top) {
                minTop = rectF.top;
            }
            if (maxLeft < rectF.left) {
                maxLeft = rectF.left;
            }
            if (maxTop < rectF.top) {
                maxTop = rectF.top;
            }
        }

        RectF rectF = new RectF();
        rectF.left = minLeft;
        rectF.top = minTop;
        rectF.right = maxLeft + Const.squareSize;
        rectF.bottom = maxTop + Const.squareSize;
        return rectF;
    }

    public PointF nearestMatrix(Shape shape) {

        RectF rectF = getRealShapeRect(shape);
        PointF pointF = new PointF();
        int i = 0;
        int j = 0;
        while (i < points.length - 1 && j < points[i].length - 1) {
            if (points[i][j].x <= rectF.left && points[i + 1][j].x > rectF.left) {
                if (rectF.left - points[i][j].x <= points[i + 1][j].x - rectF.left) {
                    pointF.x = points[i][j].x;
                } else {
                    pointF.x = points[i + 1][j].x;
                }

            }
            if (points[i][j].y <= rectF.top && points[i][j + 1].y > rectF.top) {
                if (rectF.top - points[i][j].y <= points[i][j + 1].y - rectF.top) {
                    pointF.y = points[i][j].y;
                } else {
                    pointF.y = points[i][j + 1].y;
                }
            }
            i++;
            if (i >= points.length - 1) {
                i = 0;
                j++;
            }
        }
        return pointF;
    }

    public ShapePreview drawPath(Shape shape) {
        int length = 0;
        for (int[] ints : shape.getStruct()) {
            for (int anInt : ints) {
                if (anInt == 1) length++;
            }
        }
        ShapePreview[] ps = new ShapePreview[length];
        ShapePreview p = new ShapePreview();
        int count = 0;
        int i = 0;
        int j = 0;
        while (i < shape.getStruct().length && j < shape.getStruct()[i].length) {
            if (shape.getStruct()[i][j] == 1) {
                ps[count] = new ShapePreview();
                ps[count].moveTo(i * Const.squareSize, j * Const.squareSize);
                ps[count].lineTo(Const.squareSize * (i + 1), j * Const.squareSize);
                ps[count].lineTo(Const.squareSize * (i + 1), Const.squareSize * (j + 1));
                ps[count].lineTo(i * Const.squareSize, Const.squareSize * (j + 1));
                p.childRect.add(
                        new RectF(
                                i * Const.squareSize,
                                j * Const.squareSize,
                                Const.squareSize * (i + 1),
                                Const.squareSize * (j + 1)
                        )
                );
                count++;
            }
            i++;
            if (i >= shape.getStruct().length) {
                i = 0;
                j++;
            }
        }
        for (ShapePreview p1 : ps) {
            p.addPath(p1);
        }
        return p;
    }


    public boolean addShape(Shape shape) {
        if (previewPoint != null && grayShape != null && isContainShape(grayShape)) {
            Point point = new Point();
            for (int i = 0; i < points.length; i++) {
                for (int j = 0; j < points[i].length; j++) {
                    if (previewPoint.x == points[i][j].x && previewPoint.y == points[i][j].y) {
                        point.x = i;
                        point.y = j;
                        break;
                    }
                }
            }
            int[][] temp = mapMatrix;
            if (checkByPoint(shape, point)) {
                for (int i = 0; i < shape.getStruct().length; i++) {
                    for (int j = 0; j < shape.getStruct()[i].length; j++) {
                        if (shape.getStruct()[i][j] == 1)
                            temp[i + point.x][j + point.y] = shape.getStruct()[i][j];
                    }
                }
            } else return false;
            for (int i = 0; i < mapMatrix.length; i++) {
                System.arraycopy(temp[i], 0, mapMatrix[i], 0, mapMatrix[i].length);
            }
            copyMapMatrix();
            List<Integer> listColFull = new ArrayList<>();
            List<Integer> listRowFull = new ArrayList<>();
            for (int i = 0; i < tableMatrix.length; i++) {
                for (int j = 0; j < tableMatrix[i].length; j++) {
                    if (tableMatrix[i][j].number == 1 && tableMatrix[i][j].cellSquare.getBitmap() == null) {
                        tableMatrix[i][j].cellSquare.setBitmap(BitmapContainer.getInstance().squaresBitmap[shape.getNumberColor()]);
                        if (isFullRow(j)) listRowFull.add(j);
                    }
                }
                if (isFullCol(i)) listColFull.add(i);
            }

            for (Integer integer : listRowFull) {
                removeRowAt(integer);
                dropSquare();
            }
            for (Integer integer : listColFull) {
                removeColAt(integer);
                dropSquare();
            }
            return true;
        }
        return false;
    }

    private void checkTable() {
        for (int i = 0; i < mapMatrix.length; i++) {
            for (int j = 0; j < mapMatrix[i].length; j++) {
                checkColAndRowAt(i, j);
            }
        }
    }
    private void checkColAndRowAt(int col, int row) {
        boolean isFullCol = isFullCol(col);
        boolean isFullRow = isFullRow(row);
        if (isFullCol && isFullRow) {
            removeColAt(col);
            removeRowAt(row);
            dropSquare();
        } else if (isFullCol || isFullRow) {
            if (isFullCol) {
                removeColAt(col);
            } else {
                removeRowAt(row);
            }
            dropSquare();
        }

    }

    private void removeRowAt(int row) {
        for (int i = 0; i < Const.TAB_COL; i++) {
            this.mapMatrix[i][row] = 0;
        }
        copyMapMatrix();
    }
    private void removeColAt(int col) {
        for (int i = 0; i < Const.TAB_ROW; i++) {
            this.mapMatrix[col][i] = 0;
        }
        copyMapMatrix();
    }

    private void dropSquare() {
        int i = 0;
        int j = 0;
        while (i < tableMatrix.length && j < tableMatrix[i].length) {
            if (tableMatrix[i][j].number != 1 && tableMatrix[i][j].cellSquare.getBitmap() != null){
                tableMatrix[i][j].cellSquare.setNull();
            }
            i++;
            if (i >= tableMatrix.length) {
                i = 0;
                j++;
            }
        }
    }

    private void copyMapMatrix() {
        for (int i = 0; i < mapMatrix.length; i++) {
            for (int j = 0; j < mapMatrix[i].length; j++) {
                tableMatrix[i][j].number = mapMatrix[i][j];
            }
        }
    }


    public boolean tryAdd(Shape shape) {
        Point point = new Point();
        for (int i = 0; i < mapMatrix.length; i++) {
            for (int j = 0; j < mapMatrix[i].length; j++) {
                point.x = i;
                point.y = j;
                if (checkByPoint(shape, point)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkByPoint(Shape shape, Point point) {

        for (int i = 0; i < shape.getStruct().length; i++) {
            for (int j = 0; j < shape.getStruct()[i].length; j++) {
                if (i + point.x >= mapMatrix.length || j + point.y >= mapMatrix[0].length)
                    return false;
                if (shape.getStruct()[i][j] == 1 && mapMatrix[i + point.x][j + point.y] == 1)
                    return false;
            }
        }
        return true;
    }

    public void shapeDestroy() {
        path = null;
        grayShape = null;
    }

//    private final List<Square> listClear = new ArrayList<>();
//
//    public void clearSquare(Square square) {
////        for (int i = 0; i < allSquares.length; i++) {
////            for (int j = 0; j < allSquares[i].length; j++) {
////                if (allSquares[i][j] == square) allSquares[i][j] = null;
////            }
////        }
////        if (listClear.size() >= 30) listClear.clear();
////        listClear.add(square);
////        listSquare.remove(square);
//        square = null;
//    }

    public void clearSquare() {
        listener.onRemove();
    }

    public TableListener getListener() {
        return listener;
    }

    class ShapePreview extends Path {
        final List<RectF> childRect = new ArrayList<>();
        public ShapePreview() {
        }
        public ShapePreview(@Nullable Path src) {
            super(src);
        }
    }

    class TableMatrix {
        volatile int number = 0;
        Square cellSquare;
        public TableMatrix() {
        }
    }
}
