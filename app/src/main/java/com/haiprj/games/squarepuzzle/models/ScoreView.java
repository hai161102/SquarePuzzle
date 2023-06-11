package com.haiprj.games.squarepuzzle.models;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.haiprj.games.squarepuzzle.R;

public class ScoreView extends RectF {

    private final Paint highScorePaint;
    private final Paint scorePaint;

    private final String highScoreText = "High Score: ";
    private final String scoreText = "Score: ";
    private int score = 0;
    private final float textSize = 36;
    private final float padding = 24;
    private int highScore;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ScoreView(Context context, float left, float top, float right) {
        super(left, top, right, 0);
        highScorePaint = new Paint();
        scorePaint = new Paint();

        highScorePaint.setTypeface(context.getResources().getFont(R.font.threedisometric_black_bwppg));
        highScorePaint.setTextSize(textSize);
        highScorePaint.setColor(Color.parseColor("#FAB400"));

        scorePaint.setTypeface(context.getResources().getFont(R.font.threedisometric_black_bwppg));
        scorePaint.setTextSize(textSize);
        scorePaint.setColor(Color.parseColor("#00FA37"));

        Rect textRect = new Rect();
        highScorePaint.getTextBounds(highScoreText, 0, 1, textRect);
        this.bottom = this.top + padding * 2 + textRect.height();
    }

    public void update(int score, int highScore) {
        this.score = score;
        this.highScore = score;
    }

    public void draw(Canvas canvas) {
//        String value = highScoreText;
//        float[] highValue = new float[highScoreText.length()];
//        highScorePaint.getTextWidths(highScoreText, 0, highScoreText.length(), highValue);
//        int highScoreWidth = 0;
//        for (float v : highValue) {
//            highScoreWidth += v;
//        }

        String highScoreTextValue = this.highScoreText + this.highScore;
        canvas.drawText(highScoreTextValue, this.left + padding, this.centerY(), highScorePaint);

        String value = scoreText + this.score;
        Rect scoreRect = new Rect();
        scorePaint.getTextBounds(value, 0, value.length(), scoreRect);
        canvas.drawText(value, this.right - scoreRect.width() - padding, this.centerY(), scorePaint);
    }
}
