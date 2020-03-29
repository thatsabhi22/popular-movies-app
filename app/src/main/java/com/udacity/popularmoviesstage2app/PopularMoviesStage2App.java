package com.udacity.popularmoviesstage2app;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class PopularMoviesStage2App extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }
}
