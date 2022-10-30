package com.example.to_do;

import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.Context;

import androidx.appcompat.app.AppCompatDelegate;

//import Custom Font injection through styles and attributes frame work
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

public class AppController extends Application implements ComponentCallbacks2 {

    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //use styles and attributes frame work
        ViewPump.init(
                ViewPump.builder()
                        .addInterceptor(
                                new CalligraphyInterceptor(
                                        new CalligraphyConfig.Builder()
                                                .setDefaultFontPath("fonts/nunito_medium.ttf")
                                                .setFontAttrId(R.attr.fontPath)
                                                .build()
                                )
                        )
                        .build()
        );
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}

