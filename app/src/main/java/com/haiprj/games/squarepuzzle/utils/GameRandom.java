package com.haiprj.games.squarepuzzle.utils;


import java.util.Random;

public class GameRandom{
    private static GameRandom instance;
    private Random random;
    private int bounds = 0;
    private int beforeNumber;
    private GameRandom(){
        random = new Random();
    }
    public static GameRandom getInstance() {
        if (instance == null) instance = new GameRandom();
        return instance;
    }

    public void init(int bounds) {
        this.bounds = bounds;
    }

    public int nextInt() {
        int num = this.random.nextInt(bounds);
        if (beforeNumber == num){
            num = nextInt();
        }
        else beforeNumber = num;
        return num;
    }
}
