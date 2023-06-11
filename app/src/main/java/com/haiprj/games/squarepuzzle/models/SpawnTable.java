package com.haiprj.games.squarepuzzle.models;

import android.graphics.Canvas;
import android.graphics.RectF;

import com.haiprj.games.squarepuzzle.interfaces.OnAddCallback;
import com.haiprj.games.squarepuzzle.ui.widget.GameSurface;

public class SpawnTable extends RectF {

    private final float padding = 10f;
    private final SpawnSpace[] spawnSpaces;
    private GameSurface gameSurface;
    private final Table table;

    public SpawnTable(GameSurface gameSurface, float l, float t, float r, float b) {
        super(l, t, r, b);
        this.gameSurface = gameSurface;
        this.table = this.gameSurface.table;
        spawnSpaces  = new SpawnSpace[3];
        updateShapeSpawn();
    }

    private void updateShapeSpawn() {
        float divider = this.width() / 3f;
        for (int i = 0; i < spawnSpaces.length; i++) {
            float l = this.left + i * divider;
            float t = this.top;
            int currentIndex = i;
            Shape.ShapeListener shapeListener = new Shape.ShapeListener() {
                @Override
                public void onDestroy() {
                    table.shapeDestroy();
                    spawnSpaces[currentIndex].addNewShape();
                }

                @Override
                public void onTouchMove() {
                    table.update(spawnSpaces[currentIndex].getShape());
                }

                @Override
                public void onTouchUp() {
                    table.addShape(spawnSpaces[currentIndex].getShape(), new OnAddCallback() {
                        @Override
                        public void onAddDone() {
                            spawnSpaces[currentIndex].getShape().destroy();
                            SpawnTable.this.gameSurface.checkMaybeAdd();
                        }

                        @Override
                        public void onAddFailure() {
                            spawnSpaces[currentIndex].resetToDefaultPoint();

                        }
                    });

                }
            };

            spawnSpaces[i] = new SpawnSpace(l, t,l + divider, 0, shapeListener);

            if (i == 1) {
                spawnSpaces[i].setFlipBitmap();
            }
        }
    }

    public void draw(Canvas canvas) {

        if (spawnSpaces[0].getShape().isTouch()) {
            spawnSpaces[1].draw(canvas);
            spawnSpaces[2].draw(canvas);
            spawnSpaces[0].draw(canvas);
            return;
        }
        if (spawnSpaces[1].getShape().isTouch()) {
            spawnSpaces[0].draw(canvas);
            spawnSpaces[2].draw(canvas);
            spawnSpaces[1].draw(canvas);
            return;

        }
        spawnSpaces[0].draw(canvas);
        spawnSpaces[1].draw(canvas);
        spawnSpaces[2].draw(canvas);
    }

    public void update() {
        for (SpawnSpace space : spawnSpaces) {
            space.update();
        }
    }

    public SpawnSpace[] getSpawnSpaces() {
        return spawnSpaces;
    }

    public void reset() {
        for (SpawnSpace spawnSpace : spawnSpaces) {
            spawnSpace.addNewShape();
        }
    }
}
