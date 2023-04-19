package com.haiprj.games.squarepuzzle.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.haiprj.games.squarepuzzle.R;
import com.haiprj.games.squarepuzzle.base.view.BaseActivity;
import com.haiprj.games.squarepuzzle.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }
    @Override
    protected void initView() {
    }

    @Override
    protected void addEvent() {
        binding.startGame.setOnClickListener(v -> GameActivity.start(this));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }
}