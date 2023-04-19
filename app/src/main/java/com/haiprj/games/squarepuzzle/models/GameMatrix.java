package com.haiprj.games.squarepuzzle.models;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.utils.GameRandom;

public class GameMatrix {
    private static GameMatrix instance;

    public final int[][] L_SHAPE = {
            {1, 1, 1},
            {0, 0, 1}
    };

    public final int[][] FLIP_L_SHAPE = {
            {0, 0, 1},
            {1, 1, 1}
    };

    public final int[][] L_SHAPE_ROTATE = {
            {1, 0},
            {1, 0},
            {1, 1}
    };

    public final int[][] FLIP_L_SHAPE_ROTATE = {
            {0, 1},
            {0, 1},
            {1, 1}
    };
    public final int[][] SQUARE2 = {
            {1, 1},
            {1, 1}
    };

    public final int[][] SQUARE3 = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    };

    public final int[][] RECTANGLE_H = {
            {1, 1},
            {1, 1},
            {1, 1}

    };

    public final int[][] RECTANGLE_V = {
            {1, 1, 1},
            {1, 1, 1}
    };

    public final int[][] LINE2_H = {
            {1},
            {1}
    };

    public final int[][] LINE3_H = {
            {1},
            {1},
            {1}
    };

    public final int[][] LINE2_V = {
            {1, 1}
    };
    public final int[][] LINE3_V = {
            {1, 1, 1}
    };

    public final int[][] DOT = {
            {1}
    };

    public final int[][] TABLE = new int[Const.TAB_COL][Const.TAB_ROW];
    private GameMatrix() {
        int i = 0;
        int j = 0;
        while (i < TABLE.length && j < TABLE[i].length) {
            TABLE[i][j] = 0;
            i++;
            if (i >= TABLE.length) {
                i = 0;
                j ++;
            }
        }
    }

    public static GameMatrix getInstance() {
        if (instance == null) instance = new GameMatrix();
        return instance;
    }

    public int count() {
        return 13;
    }

    public int[][] getStruct(int index) {
        switch (index) {
            case 0:
                return L_SHAPE;
            case 1:
                return FLIP_L_SHAPE;
            case 2:
                return SQUARE2;
            case 3:
                return SQUARE3;
            case 4:
                return RECTANGLE_H;
            case 5:
                return RECTANGLE_V;
            case 6:
                return LINE2_H;
            case 7:
                return LINE3_H;
            case 8:
                return LINE2_V;
            case 9:
                return LINE3_V;
            case 10:
                return DOT;
            case 11:
                return L_SHAPE_ROTATE;
            case 12:
                return FLIP_L_SHAPE_ROTATE;
            default:
                return null;
        }
    }
}
