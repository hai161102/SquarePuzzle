package com.haiprj.games.squarepuzzle;

import com.haiprj.android_app_lib.MyApplication;
import com.haiprj.games.squarepuzzle.base.utils.GameSharePreference;
import com.haiprj.games.squarepuzzle.models.GameMatrix;
import com.haiprj.games.squarepuzzle.utils.GameRandom;

public class Game extends MyApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        GameRandom.getInstance().init(GameMatrix.getInstance().count());
        GameSharePreference.getInstance().init(this);
    }

    @Override
    protected boolean isPurchased() {
        return false;
    }

    @Override
    protected boolean isShowAdsTest() {
        return false;
    }

    @Override
    public boolean enableAdsResume() {
        return false;
    }

    @Override
    public String getOpenAppAdId() {
        return null;
    }
}
