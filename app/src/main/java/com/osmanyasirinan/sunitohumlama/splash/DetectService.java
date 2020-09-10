package com.osmanyasirinan.sunitohumlama.splash;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.osmanyasirinan.sunitohumlama.Utils;

public class DetectService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("DetectService", "STARTED");
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DetectService", "DESTROYED");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.e("DetectService", "END");
        Utils.cleanPrefs(getApplicationContext());
        stopSelf();
    }
}
