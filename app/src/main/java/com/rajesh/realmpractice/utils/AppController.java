package com.rajesh.realmpractice.utils;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppController extends Application {
    private static AppController mInstance;
    private Context context;

    public static synchronized AppController getmInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("RealmPracticeDB.realm").build();
        Realm.setDefaultConfiguration(config);
        context = getApplicationContext();
        mInstance = this;
    }

    public Context getMainContext() {
        return context;
    }
}
