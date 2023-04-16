package com.haiprj.games.squarepuzzle.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import androidx.annotation.Nullable;

public class LoadBitmapUtil extends AsyncTask<Void, Void, Bitmap[]> {

    @SuppressLint("StaticFieldLeak")
    private final Context context;
    private int[] imageIds;
    private OnLoadListener listener;

    public LoadBitmapUtil(Context context, OnLoadListener listener, int... imageIds) {
        this.context = context;
        this.imageIds = imageIds;
        this.listener = listener;
    }

    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This will normally run on a background thread. But to better
     * support testing frameworks, it is recommended that this also tolerates
     * direct execution on the foreground thread, as part of the {@link #execute} call.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @SuppressWarnings("BusyWait")
    @Override
    protected Bitmap[] doInBackground(@Nullable Void... voids) {
        listener.onLoad();
        int i = 0;
        Bitmap[] listBitmap = new Bitmap[imageIds.length];
        while (i < imageIds.length) {
            listBitmap[i] = BitmapFactory.decodeResource(this.context.getResources(), imageIds[i]);
            if (listBitmap[i] == null) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {

                }
            }
            if (listBitmap[i] != null) {
                i++;
            }
        }
        return listBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap[] bitmaps) {
        super.onPostExecute(bitmaps);
        listener.onDone(bitmaps);
    }

    public interface OnLoadListener {
        void onLoad();
        void onDone(Bitmap[] bitmaps);
    }
}
