package com.zii.canvas;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * @create Created by Zii on 2018/2/24.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
