package com.haiprj.games.squarepuzzle.base.animation;

public abstract class BaseAnimation implements Runnable {

    protected Thread animationThread;
    protected int FPS;
    protected AnimationActionListener listener;

    public BaseAnimation(int FPS, AnimationActionListener listener) {
        this.FPS = FPS;
        this.listener = listener;
        animationThread = new Thread(this);
    }

    public BaseAnimation(int FPS) {
        this.FPS = FPS;
        animationThread = new Thread(this);
    }

    @SuppressWarnings("BusyWait")
    @Override
    public void run() {
        double drawInterval = 1000000000f / FPS;
        double nextTime = System.nanoTime() + drawInterval;
        listener.onStart();
        while (animationThread != null) {
            double remainTime = nextTime - System.nanoTime();
            remainTime /= 1000000;

            listener.onUpdate();

            try {
                if (remainTime < 0) {
                    remainTime = 0;
                }

                Thread.sleep((long) remainTime);
                nextTime += drawInterval;
            } catch (InterruptedException ignored) {
                animationThread.interrupt();
                animationThread = null;
                listener.onEnd();
            }
        }
    }

    public void start() {
        if (animationThread == null) animationThread = new Thread(this);
        animationThread.start();
    }

    public void end() {
        if (animationThread != null) animationThread.interrupt();
        animationThread = null;
        listener.onEnd();
    }

    public interface AnimationActionListener {
        void onStart();
        void onUpdate();
        void onEnd();
    }
}
