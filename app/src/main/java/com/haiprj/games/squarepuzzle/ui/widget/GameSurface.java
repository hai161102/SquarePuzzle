package com.haiprj.games.squarepuzzle.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.base.widget.BaseGameSurface;
import com.haiprj.games.squarepuzzle.models.Shape;
import com.haiprj.games.squarepuzzle.models.SpawnSpace;
import com.haiprj.games.squarepuzzle.models.SpawnTable;
import com.haiprj.games.squarepuzzle.models.Table;

public class GameSurface extends BaseGameSurface {

    private Table table;
    private final int padding = 50;
    private SpawnTable spawnTable;

    public GameSurface(Context context) {
        super(context);
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void init() {
        super.init();
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void initView(SurfaceHolder surfaceHolder) {
        Const.squareSize = (this.getWidth() - padding * 2) / Const.TAB_COL;
        table = new Table(padding, padding);
        spawnTable = new SpawnTable(table.left, table.bottom);
    }

    @Override
    protected void update() {
        if (spawnTable != null)
            spawnTable.update();
        if (table != null) {
            assert spawnTable != null;
//            table.update(spawnSpace.getShape());
        }
    }

    @Override
    protected void gameDraw(Canvas canvas) {
        table.draw(canvas);
        spawnTable.draw(canvas);
    }


    @Override
    protected void onTouch(MotionEvent event) {
        super.onTouch(event);
        spawnTable.getSpaceFirst().getShape().onTouch(event);
        spawnTable.getSpaceSecond().getShape().onTouch(event);
        spawnTable.getSpaceThird().getShape().onTouch(event);
        //if (shape.contains(event.getX(), event.getY()))
    }
}
