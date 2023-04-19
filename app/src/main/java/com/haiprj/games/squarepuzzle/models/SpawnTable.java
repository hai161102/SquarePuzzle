package com.haiprj.games.squarepuzzle.models;

import android.graphics.Canvas;
import android.graphics.RectF;

public class SpawnTable extends RectF {

    private final float padding = 10f;
    private final SpawnSpace[] spawnSpaces;
    private final Table table;

    public SpawnTable(Table table, float l, float t, float r, float b) {
        super(l, t, r, b);
        this.table = table;
        spawnSpaces  = new SpawnSpace[3];
        updateShapeSpawn();
//        spaceFirst = new SpawnSpace(this.left, this.top,
//                new Shape.ShapeListener() {
//                    @Override
//                    public void onAdd() {
//                        if (!table.addShape(spaceFirst.getShape())) {
//                            spaceFirst.resetToDefaultPoint();
//                        } else {
//                            table.getListener().onAdd();
//                            spaceFirst.addNewShape();
//                        }
//                    }
//
//                    @Override
//                    public void onDestroy() {
//                        table.shapeDestroy();
//                    }
//                });
//        spaceSecond = new SpawnSpace(spaceFirst.right, spaceFirst.top, new Shape.ShapeListener() {
//            @Override
//            public void onAdd() {
//                if (!table.addShape(spaceSecond.getShape())) {
//                    spaceSecond.resetToDefaultPoint();
//                } else {
//                    table.getListener().onAdd();
//                    spaceSecond.addNewShape();
//                }
//            }
//
//            @Override
//            public void onDestroy() {
//                table.shapeDestroy();
//            }
//        });
//        spaceThird = new SpawnSpace(spaceSecond.right, spaceSecond.top, new Shape.ShapeListener() {
//            @Override
//            public void onAdd() {
//                if (!table.addShape(spaceThird.getShape())) {
//                    spaceThird.resetToDefaultPoint();
//
//                } else {
//                    table.getListener().onAdd();
//                    spaceThird.addNewShape();
//                }
//            }
//
//            @Override
//            public void onDestroy() {
//                table.shapeDestroy();
//            }
//        });
    }

    private void updateShapeSpawn() {
        float divider = this.width() / 3f;
        for (int i = 0; i < spawnSpaces.length; i++) {
            float l = this.left + i * divider;
            float t = this.top;
            int finalI = i;
            Shape.ShapeListener shapeListener = new Shape.ShapeListener() {
                @Override
                public void onDestroy() {
                    table.shapeDestroy();
                    spawnSpaces[finalI].addNewShape();
                }

                @Override
                public void onTouchMove() {
                    table.update(spawnSpaces[finalI].getShape());
                }

                @Override
                public void onTouchUp() {
                    boolean isAdded = table.onDropShape(spawnSpaces[finalI].getShape());
                    if (isAdded) {
                        table.onAdd();
                        spawnSpaces[finalI].getShape().destroy();
                    }
                    else {
                        spawnSpaces[finalI].resetToDefaultPoint();
                    }
                }
            };

            spawnSpaces[i] = new SpawnSpace(l, t,l + divider, 0, shapeListener);
        }
    }
//
//    public void updateSize(float left, float top) {
//        this.left = left;
//        this.top = top;
//        float w = 0;
//        for (SpawnSpace spawnSpace : spawnSpaces) {
//            w += spawnSpace.width();
//        }
//        this.right = left + w;
//        this.bottom = top + spawnSpaces[0].height();
//        updateShapeSpawn();
//    }

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
//
//    public SpawnSpace getSpaceFirst() {
//        return spaceFirst;
//    }
//
//    public SpawnSpace getSpaceSecond() {
//        return spaceSecond;
//    }
//
//    public SpawnSpace getSpaceThird() {
//        return spaceThird;
//    }
}
