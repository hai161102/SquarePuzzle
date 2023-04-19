package com.haiprj.games.squarepuzzle.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.base.widget.BaseGameSurface;
import com.haiprj.games.squarepuzzle.interfaces.TableListener;
import com.haiprj.games.squarepuzzle.models.Shape;
import com.haiprj.games.squarepuzzle.models.SpawnSpace;
import com.haiprj.games.squarepuzzle.models.SpawnTable;
import com.haiprj.games.squarepuzzle.models.Table;

public class GameSurface extends BaseGameSurface {

    private final Table table;
    private final int padding = 96;
    private SpawnTable spawnTable;
    private int score = 0;
    public GameSurface(Context context) {
        super(context);
        table = new Table(padding, padding);
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        table = new Table(padding, padding);

    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        table = new Table(padding, padding);

    }

    public GameSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        table = new Table(padding, padding);
    }

    @Override
    protected void init() {
        super.init();
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void initView(SurfaceHolder surfaceHolder) {
        Const.squareSize = (this.getWidth() - padding * 2) / Const.TAB_COL;
        table.setListener(new TableListener() {
            @Override
            public void onRemove() {
                score++;
            }

            @Override
            public void onAdd() {
                if (
                        !table.tryAdd(spawnTable.getSpaceFirst().getShape())
                                && !table.tryAdd(spawnTable.getSpaceSecond().getShape())
                                && !table.tryAdd(spawnTable.getSpaceThird().getShape())
                ){
                    gameOver();
                }
            }
        });
        spawnTable = new SpawnTable(table.left, table.bottom + padding, table);
    }

    @Override
    protected void update() {
        if (spawnTable != null)
            spawnTable.update();
        if (table != null) {
            table.update();
        }
    }

    private void gameOver() {
        Log.d("GameOver", "gameOver: ");
        Toast.makeText(this.getContext(), "Game Over", Toast.LENGTH_LONG).show();
        isGameOver = true;
//        stopThread();
    }

    @Override
    protected void gameDraw(Canvas canvas) {
        if (table != null) {
            table.draw(canvas);
        }
        if (spawnTable != null)
            spawnTable.draw(canvas);
    }


    @Override
    protected void onTouch(MotionEvent event) {
        super.onTouch(event);
        if (spawnTable != null) {
            spawnTable.getSpaceFirst().getShape().onTouch(event);
            spawnTable.getSpaceSecond().getShape().onTouch(event);
            spawnTable.getSpaceThird().getShape().onTouch(event);
        }
        //if (shape.contains(event.getX(), event.getY()))
    }

}
