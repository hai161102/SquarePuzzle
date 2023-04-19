package com.haiprj.games.squarepuzzle.models;

import android.graphics.Canvas;
import android.graphics.RectF;

public class SpawnTable extends RectF {

    private final float padding = 10f;
    private final SpawnSpace spaceFirst;
    private final SpawnSpace spaceSecond;
    private final SpawnSpace spaceThird;
    private final Table table;

    public SpawnTable(float left, float top, Table table) {
        super(left, top, 0, 0);
        this.table = table;
        spaceFirst = new SpawnSpace(this.left, this.top,
                new Shape.ShapeListener() {
                    @Override
                    public void onAdd() {
                        if (!table.addShape(spaceFirst.getShape())) {
                            spaceFirst.resetToDefaultPoint();
                        } else {
                            table.getListener().onAdd();
                            spaceFirst.addNewShape();
                        }
                    }

                    @Override
                    public void onDestroy() {
                        table.shapeDestroy();
                    }
                });
        spaceSecond = new SpawnSpace(spaceFirst.right, spaceFirst.top, new Shape.ShapeListener() {
            @Override
            public void onAdd() {
                if (!table.addShape(spaceSecond.getShape())) {
                    spaceSecond.resetToDefaultPoint();
                } else {
                    table.getListener().onAdd();
                    spaceSecond.addNewShape();
                }
            }

            @Override
            public void onDestroy() {
                table.shapeDestroy();
            }
        });
        spaceThird = new SpawnSpace(spaceSecond.right, spaceSecond.top, new Shape.ShapeListener() {
            @Override
            public void onAdd() {
                if (!table.addShape(spaceThird.getShape())) {
                    spaceThird.resetToDefaultPoint();

                } else {
                    table.getListener().onAdd();
                    spaceThird.addNewShape();
                }
            }

            @Override
            public void onDestroy() {
                table.shapeDestroy();
            }
        });
    }

    public void draw(Canvas canvas) {

        if (spaceFirst.getShape().isTouch) {
            spaceSecond.draw(canvas);
            spaceThird.draw(canvas);
            spaceFirst.draw(canvas);
            return;
        }
        if (spaceSecond.getShape().isTouch) {
            spaceFirst.draw(canvas);
            spaceThird.draw(canvas);
            spaceSecond.draw(canvas);
            return;

        }
        spaceFirst.draw(canvas);
        spaceSecond.draw(canvas);
        spaceThird.draw(canvas);
    }

    public void update() {
        spaceFirst.update();
        spaceSecond.update();
        spaceThird.update();
        if (table != null) {
            if (getSpaceFirst().getShape().isTouch()) {
                table.update(getSpaceFirst().getShape());
            } else if (getSpaceSecond().getShape().isTouch()) {
                table.update(getSpaceSecond().getShape());
            } else if (getSpaceThird().getShape().isTouch()) {
                table.update(getSpaceThird().getShape());
            }
//            table.update(spawnSpace.getShape());
        }
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
