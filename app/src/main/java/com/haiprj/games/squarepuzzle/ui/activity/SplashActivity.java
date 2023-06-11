package com.haiprj.games.squarepuzzle.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;

import com.haiprj.games.squarepuzzle.R;
import com.haiprj.games.squarepuzzle.base.utils.LoadBitmapUtil;
import com.haiprj.games.squarepuzzle.base.view.BaseActivity;
import com.haiprj.games.squarepuzzle.databinding.ActivitySplashBinding;
import com.haiprj.games.squarepuzzle.utils.BitmapContainer;
import com.haiprj.games.squarepuzzle.utils.GameRandom;
import com.haiprj.games.squarepuzzle.utils.LoadBitmapStream;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override
    protected void initView() {
        loadImageSquare();
        loadBoard();
        loadGraySquare();
        loadBitmapSquareDestroy();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(SplashActivity.this);
                finish();
            }
        }, 3000);
    }

    @Override
    protected void addEvent() {

    }

    private void loadImageSquare() {
        new LoadBitmapStream(this, new LoadBitmapUtil.OnLoadListener() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onDone(Bitmap[] bitmaps) {
                BitmapContainer.getInstance().squaresBitmap = bitmaps;
            }
        },
                "images/red.png",
                "images/orange.png",
                "images/yellow.png",
                "images/pink.png",
                "images/green_lower.png",
                "images/green_higher.png",
                "images/blue.png"
        )
                .execute();
    }
    private void loadBoard() {
        new LoadBitmapStream(this, new LoadBitmapUtil.OnLoadListener() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onDone(Bitmap[] bitmaps) {
                BitmapContainer.getInstance().boardBitmap = bitmaps[0];
            }
        },
                "images/board.png")
                .execute();
        new LoadBitmapStream(this, new LoadBitmapUtil.OnLoadListener() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onDone(Bitmap[] bitmaps) {
                BitmapContainer.getInstance().borderBoardBitmap = bitmaps[0];
            }
        },"images/border_board.png").execute();
        new LoadBitmapStream(this, new LoadBitmapUtil.OnLoadListener() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onDone(Bitmap[] bitmaps) {
                BitmapContainer.getInstance().borderSpawnBitmap = bitmaps[0];
            }
        },"images/border_spawn.png").execute();
    }
    public void loadGraySquare() {
        new LoadBitmapStream(this, new LoadBitmapUtil.OnLoadListener() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onDone(Bitmap[] bitmaps) {
                BitmapContainer.getInstance().gray = bitmaps[0];
            }
        }, "images/gray.png").execute();
    }
    private void loadBitmapSquareDestroy() {
        new LoadBitmapStream(this, new LoadBitmapUtil.OnLoadListener() {
            @Override
            public void onLoad() {

            }

            @Override
            public void onDone(Bitmap[] bitmaps) {
                BitmapContainer.getInstance().bitmapsSquareDestroy = bitmaps;
            }
        },
             "images/destroys/gray.png",
             "images/destroys/gray-1.png",
             "images/destroys/gray-2.png",
             "images/destroys/gray-3.png",
             "images/destroys/gray-4.png"
        ).execute();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
