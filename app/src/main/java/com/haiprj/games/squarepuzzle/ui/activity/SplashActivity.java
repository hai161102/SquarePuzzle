package com.haiprj.games.squarepuzzle.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;

import com.haiprj.games.squarepuzzle.R;
import com.haiprj.games.squarepuzzle.base.utils.LoadBitmapUtil;
import com.haiprj.games.squarepuzzle.base.view.BaseActivity;
import com.haiprj.games.squarepuzzle.databinding.ActivitySplashBinding;
import com.haiprj.games.squarepuzzle.utils.BitmapContainer;
import com.haiprj.games.squarepuzzle.utils.LoadBitmapStream;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override
    protected void initView() {
        loadImageSquare();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.start(SplashActivity.this);
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
                "images/sprite_square0.png",
                "images/sprite_square1.png",
                "images/sprite_square2.png",
                "images/sprite_square3.png",
                "images/sprite_square4.png",
                "images/sprite_square5.png"
        )
                .execute();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }
}
