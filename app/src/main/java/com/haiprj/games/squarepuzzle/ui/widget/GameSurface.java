package com.haiprj.games.squarepuzzle.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.Game;
import com.haiprj.games.squarepuzzle.base.utils.GameSharePreference;
import com.haiprj.games.squarepuzzle.base.widget.BaseGameSurface;
import com.haiprj.games.squarepuzzle.interfaces.TableListener;
import com.haiprj.games.squarepuzzle.models.ScoreView;
import com.haiprj.games.squarepuzzle.models.SpawnSpace;
import com.haiprj.games.squarepuzzle.models.SpawnTable;
import com.haiprj.games.squarepuzzle.models.Table;

public class GameSurface extends BaseGameSurface {

    public ScoreView scoreView;
    public Table table;
    private SpawnTable spawnTable;
    private int score = 0;
    private int highScore = 0;
    public GameCallback gameCallback;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void initView(SurfaceHolder surfaceHolder) {
        highScore = GameSharePreference.getInstance().getInt(Const.HIGHEST_SCORE, 0);
        if (scoreView == null) {
            scoreView = new ScoreView(this.getContext(), 0, 0, this.getWidth());
        }

        int padding = 64;
        Const.squareSize = (this.getHeight() * 40 / 100f - padding * 2) / Const.TAB_COL;
        float tbWidth = Const.squareSize * Const.TAB_COL;
        float tLeft = getWidth() / 2f - tbWidth / 2f;
        if (tbWidth > getWidth()) {
            Const.squareSize = (this.getWidth() - padding * 2) / Const.TAB_COL;
            tbWidth = Const.squareSize * Const.TAB_COL;
            tLeft = getWidth() / 2f - tbWidth / 2f;
        }
        if (table == null)
            table = new Table(tLeft,scoreView.bottom + padding);
        table.setListener(new TableListener() {
            @Override
            public void onRemove() {
                score++;
            }

            @Override
            public void onAdd() {

            }
        });
        if (spawnTable == null)
            spawnTable = new SpawnTable(this, table.left - padding, table.bottom + padding, table.right + padding, 0);

    }

    public void checkMaybeAdd() {
        if (isAllNotAdd()){
            gameOver();
        }
    }
    private boolean isAllNotAdd() {
        for (SpawnSpace spawnSpace : spawnTable.getSpawnSpaces()) {
            if (table.tryAdd(spawnSpace.getShape())) return false;
        }
        return true;
    }
    @Override
    protected void update() {
        if (scoreView != null) {
            if (highScore <= score) highScore = score;
            scoreView.update(score, this.highScore);
        }
        if (spawnTable != null)
            spawnTable.update();
        if (table != null) {
            table.update();
        }

    }

    private void gameOver() {
        Log.d("GameOver", "gameOver: ");
        //Toast.makeText(this.getContext(), "Game Over", Toast.LENGTH_LONG).show();
        GameSharePreference.getInstance().setInt(Const.HIGHEST_SCORE, this.highScore);
        isGameOver = true;
        if (gameCallback != null) gameCallback.onOver(this.score);
    }

    @Override
    protected void gameDraw(Canvas canvas) {
        if (scoreView != null) scoreView.draw(canvas);
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
            for (SpawnSpace spawnSpace : spawnTable.getSpawnSpaces()) {
                spawnSpace.getShape().onTouch(event);
            }
        }
    }

    public void clearAndReset() {
        score = 0;
        table.reset();
        spawnTable.reset();
        isGameOver = false;
    }

    public interface GameCallback {
        void onOver(int score);
    }
}
