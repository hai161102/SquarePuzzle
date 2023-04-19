package com.haiprj.games.squarepuzzle.base.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.NonNull;

public abstract class BaseGameSurface extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    protected static final int FPS = 60;
    protected final SurfaceHolder surfaceHolder;
    protected boolean isGameOver = false;
    protected Activity activity;
    protected GameThread gameThread;
    protected boolean isPause;

    public BaseGameSurface(Context context) {
        super(context);
        surfaceHolder = getHolder();
        init();
    }

    public BaseGameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        init();
    }

    public BaseGameSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceHolder = getHolder();
        init();
    }

    public BaseGameSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        surfaceHolder = getHolder();
        init();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    protected void init() {
        this.surfaceHolder.addCallback(this);
        this.setOnTouchListener(this);
    }

    protected abstract void initView(SurfaceHolder surfaceHolder);

    protected abstract void update();

    protected abstract void gameDraw(Canvas canvas);

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (activity != null) {
            activity.runOnUiThread(() -> gameDraw(canvas));
        }
    }

    /**
     * This is called immediately after the surface is first created.
     * Implementations of this should start up whatever rendering code
     * they desire.  Note that only one thread can ever draw into
     * a {@link Surface}, so you should not draw into the Surface here
     * if your normal rendering will be in another thread.
     *
     * @param holder The SurfaceHolder whose surface is being created.
     */
    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        startThread();
        initView(this.surfaceHolder);
    }

    /**
     * This is called immediately after any structural changes (format or
     * size) have been made to the surface.  You should at this point update
     * the imagery in the surface.  This method is always called at least
     * once, after {@link #surfaceCreated}.
     *
     * @param holder The SurfaceHolder whose surface has changed.
     * @param format The new {@link PixelFormat} of the surface.
     * @param width  The new width of the surface.
     * @param height The new height of the surface.
     */
    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        Log.d("ListenSurface", "surfaceChanged: " + (gameThread != null));
    }

    public void startThread() {
//        post(runnable);
        gameThread = new GameThread(getHolder(), this);
        gameThread.setRunning(true);
        gameThread.start();
    }

    /**
     * This is called immediately before a surface is being destroyed. After
     * returning from this call, you should no longer try to access this
     * surface.  If you have a rendering thread that directly accesses
     * the surface, you must ensure that thread is no longer touching the
     * Surface before returning from this function.
     *
     * @param holder The SurfaceHolder whose surface is being destroyed.
     */
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        Log.d("ListenSurface", "surfaceDestroyed: ");
        stopThread();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
    }

    public void stopThread() {
        gameThread.setRunning(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        removeCallbacks(runnable);
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        onTouch(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                onTouchDown(event);
                break;
            case MotionEvent.ACTION_MOVE:
                onTouchMove(event);
                break;
            case MotionEvent.ACTION_UP:
                onTouchUp(event);
                break;
        }
        return true;
    }

    protected void onTouchUp(MotionEvent event) {

    }

    protected void onTouchMove(MotionEvent event) {
    }

    protected void onTouchDown(MotionEvent event) {
    }

    protected void onTouch(MotionEvent event) {

    }

    public class GameThread extends Thread
    {
        private final SurfaceHolder holder;
        private boolean running = false;

        private final BaseGameSurface baseGameSurface;

        public GameThread(SurfaceHolder holder, BaseGameSurface baseGameSurface)
        {
            this.holder = holder;
            this.baseGameSurface = baseGameSurface;
        }

        @SuppressLint("NewApi")
        @SuppressWarnings("BusyWait")
        @Override
        public void run()
        {
            Canvas canvas = null;
            double drawInterval = 1000000000f / FPS;
            double nextTime = System.nanoTime() + drawInterval;
            while (running)
            {
                double remainTime = nextTime - System.nanoTime();
                remainTime /= 1000000;
                if (!isGameOver) {
                    baseGameSurface.updateAll(); //update the time between last update() call and now
                }
                try
                {
                    canvas = holder.lockHardwareCanvas();
                    synchronized (holder)
                    {
                        invalidate();
                    }
                    try {
                        if (remainTime < 0) {
                            remainTime = 0;
                        }

                        Thread.sleep((long) remainTime);
                        nextTime += drawInterval;
                    } catch (InterruptedException ignored) {
                        this.interrupt();
                    }

                } finally
                {
                    if (canvas != null)
                    {
                        holder.unlockCanvasAndPost(canvas);
                    }
                }


            }

        }

        public void setRunning(boolean b)
        {
            running = b;
        }
    }

    protected void render(Canvas canvas) {
        gameDraw(canvas);
    }

    protected void updateAll() {
        ((Activity) getContext()).runOnUiThread(this::update);
    }

    public void onPause() {
        stopThread();
        synchronized (this.surfaceHolder) {
            isPause = true;
        }
    }

    public void onResume() {
        if (isPause) {
            startThread();
            synchronized (this.surfaceHolder){
                isPause = false;
            }
        }
    }

}
