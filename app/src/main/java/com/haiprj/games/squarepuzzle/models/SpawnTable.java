package com.haiprj.games.squarepuzzle.models;

import android.graphics.Canvas;
import android.graphics.RectF;

public class SpawnTable extends RectF {

    private final float padding = 10f;
    private final SpawnSpace spaceFirst;
    private final SpawnSpace spaceSecond;
    private final SpawnSpace spaceThird;
    public SpawnTable(float left, float top) {
        super(left, top, 0, 0);
        spaceFirst = new SpawnSpace(this.left + padding , this.top + padding);
        spaceSecond = new SpawnSpace(spaceFirst.right, spaceFirst.top);
        spaceThird = new SpawnSpace(spaceSecond.right, spaceSecond.top);
    }

    public void draw(Canvas canvas) {
        spaceFirst.draw(canvas);
        spaceSecond.draw(canvas);
        spaceThird.draw(canvas);
    }

    public void update() {
        spaceFirst.update();
        spaceSecond.update();
        spaceThird.update();
    }

    public SpawnSpace getSpaceFirst() {
        return spaceFirst;
    }

    public SpawnSpace getSpaceSecond() {
        return spaceSecond;
    }

    public SpawnSpace getSpaceThird() {
        return spaceThird;
    }
}
