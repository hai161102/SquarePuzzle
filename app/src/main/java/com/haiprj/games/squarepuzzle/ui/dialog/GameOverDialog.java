package com.haiprj.games.squarepuzzle.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haiprj.games.squarepuzzle.Const;
import com.haiprj.games.squarepuzzle.R;
import com.haiprj.games.squarepuzzle.base.utils.GameSharePreference;
import com.haiprj.games.squarepuzzle.base.view.BaseDialog;
import com.haiprj.games.squarepuzzle.databinding.DialogGameOverBinding;

public class GameOverDialog extends BaseDialog<DialogGameOverBinding> {

    @SuppressLint("StaticFieldLeak")
    private static GameOverDialog instance;
    private int score;

    public GameOverDialog(@NonNull Context context, Activity activity, OnActionDialogCallback onActionDialogCallback) {
        super(context, activity, onActionDialogCallback);
    }

    public GameOverDialog(@NonNull Context context, int themeResId, Activity activity, OnActionDialogCallback onActionDialogCallback) {
        super(context, themeResId, activity, onActionDialogCallback);
    }

    public GameOverDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener, Activity activity, OnActionDialogCallback onActionDialogCallback) {
        super(context, cancelable, cancelListener, activity, onActionDialogCallback);
    }

    public static GameOverDialog getInstance(@NonNull Context context, Activity activity, OnActionDialogCallback onActionDialogCallback) {
        if (instance == null) instance = new GameOverDialog(context, activity, onActionDialogCallback);
        return instance;
    }

    @Override
    protected void addEvent() {
        binding.exit.setOnClickListener(v -> {
            onActionDialogCallback.callback("exit");
            dismiss();
        });

        binding.playAgain.setOnClickListener(v -> {
            onActionDialogCallback.callback("play_again");
            dismiss();
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        int highScore = GameSharePreference.getInstance().getInt(Const.HIGHEST_SCORE, 0);
        binding.highestScore.setText(getContext().getString(R.string.highest_score) + ": " + highScore);
        binding.playerScore.setText(getContext().getString(R.string.your_score) + ": " + score);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_game_over;
    }

    @SuppressLint("SetTextI18n")
    public void setScore(int score) {
        this.score = score;
    }

    public static GameOverDialog getInstance() {
        return instance;
    }
}
