package com.haiprj.games.squarepuzzle.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;

import androidx.annotation.NonNull;

import com.haiprj.games.squarepuzzle.R;
import com.haiprj.games.squarepuzzle.base.view.BaseActivity;
import com.haiprj.games.squarepuzzle.base.view.BaseDialog;
import com.haiprj.games.squarepuzzle.databinding.ActivityGameBinding;
import com.haiprj.games.squarepuzzle.ui.dialog.GameOverDialog;

public class GameActivity extends BaseActivity<ActivityGameBinding> {
    private static final String CURRENT_SURFACE = "CURRENT_SURFACE";

    public static void start(Context context) {
        Intent starter = new Intent(context, GameActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void initView() {
        binding.gameSurface.setActivity(this);
        GameOverDialog.getInstance(this, this, (key, objects) -> {

        }).show();
    }

    @Override
    protected void addEvent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_game;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
