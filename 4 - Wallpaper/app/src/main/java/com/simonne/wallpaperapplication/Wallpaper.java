package com.simonne.wallpaperapplication;

import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Wallpaper extends Service {

    WallpaperManager wpm;
    Timer timer;
    Drawable drawable;
    int count = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        wpm = WallpaperManager.getInstance(Wallpaper.this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(count == 1){
                    drawable = getResources().getDrawable(R.drawable.one);
                    count = 2;
                }
                else if(count == 2){
                    drawable = getResources().getDrawable(R.drawable.two);
                    count = 3;
                }
                else if(count == 3){
                    drawable = getResources().getDrawable(R.drawable.three);
                    count = 4;
                }
                else if(count == 4){
                    drawable = getResources().getDrawable(R.drawable.four);
                    count = 5;
                }
                else{
                    drawable = getResources().getDrawable(R.drawable.five);
                    count = 1;
                }

                Bitmap wallpaper = ((BitmapDrawable)drawable).getBitmap();
                try {
                    wpm.setBitmap(wallpaper);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 30000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}