package com.haiprj.games.squarepuzzle.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.haiprj.games.squarepuzzle.R;
import com.haiprj.games.squarepuzzle.base.view.BaseDialog;
import com.haiprj.games.squarepuzzle.databinding.DialogGameOverBinding;

public class GameOverDialog extends BaseDialog<DialogGameOverBinding> {

    @SuppressLint("StaticFieldLeak")
    private static GameOverDialog instance;

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

    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_game_over;
    }
}
