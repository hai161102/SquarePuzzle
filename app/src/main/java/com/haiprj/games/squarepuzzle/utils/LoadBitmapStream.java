package com.haiprj.games.squarepuzzle.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.haiprj.games.squarepuzzle.base.utils.LoadBitmapUtil;

import java.io.InputStream;

@SuppressWarnings("deprecation")
public class LoadBitmapStream extends AsyncTask<Void, Void, Bitmap[]> {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private final String[] filePaths;
    private final LoadBitmapUtil.OnLoadListener listener;

    public LoadBitmapStream(Context context, LoadBitmapUtil.OnLoadListener listener, String... filePaths) {
        this.context = context;
        this.listener = listener;
        this.filePaths = filePaths;
    }

    @Override
    protected Bitmap[] doInBackground(Void... voids) {
        listener.onLoad();
        AssetManager assetManager = context.getAssets();
        Bitmap[] bitmaps = new Bitmap[this.filePaths.length];
        try {
            InputStream[] inputStreams = new InputStream[filePaths.length];
            for (int i = 0; i < this.filePaths.length; i++) {
                inputStreams[i] = assetManager.open(this.filePaths[i]);
                bitmaps[i] = BitmapFactory.decodeStream(inputStreams[i]);
                inputStreams[i].close();
            }
        }
        catch (Exception ignored) {

        }
        return bitmaps;
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        super.onPostExecute(bitmaps);
        listener.onDone(bitmaps);
    }
}
