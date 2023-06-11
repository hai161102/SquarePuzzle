package com.haiprj.games.squarepuzzle.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.haiprj.games.squarepuzzle.R;
import com.haiprj.games.squarepuzzle.base.view.BaseActivity;
import com.haiprj.games.squarepuzzle.databinding.ActivityGameBinding;
import com.haiprj.games.squarepuzzle.ui.dialog.GameOverDialog;
import com.haiprj.games.squarepuzzle.ui.widget.GameSurface;

import java.util.Objects;

public class GameActivity extends BaseActivity<ActivityGameBinding> {
    private static final String CURRENT_SURFACE = "CURRENT_SURFACE";

    public static void start(Context context) {
        Intent starter = new Intent(context, GameActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void initView() {
        binding.gameSurface.setActivity(this);
        binding.gameSurface.gameCallback = new GameSurface.GameCallback() {
            @Override
            public void onOver(int score) {
                GameOverDialog.getInstance(GameActivity.this, GameActivity.this, (key, objects) -> {
                    if (Objects.equals(key, "exit")) {
                        finish();
                    }
                    if (Objects.equals(key, "play_again")) {
                        binding.gameSurface.clearAndReset();
                    }
                });
                GameOverDialog.getInstance().setScore(score);
                GameOverDialog.getInstance().show();

            }
        };
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
