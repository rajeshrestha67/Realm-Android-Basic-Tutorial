package com.rajesh.realmpractice.utils;

import android.app.Application;
import android.content.Context;

import com.rajesh.realmpractice.database.migration.RealmMigrationHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppController extends Application {
    private static AppController mInstance;
    private Context context;
    private Realm realm;

    public static synchronized AppController getmInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        /**
         * schemaVersion starts from 0
         */

      /*  RealmConfiguration config = new RealmConfiguration.Builder()
                .name("RealmPracticeDB.realm")
                .build();*/
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("RealmPracticeDB.realm")
                .schemaVersion(1)
                .migration(new RealmMigrationHelper())
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getInstance(config);
        context = getApplicationContext();
        mInstance = this;
    }

    public Realm getRealm() {
        return realm;
    }

    @Override
    public void onTerminate() {
        Realm.getDefaultInstance().close();
        super.onTerminate();

    }

    public Context getMainContext() {
        return context;
    }
}
