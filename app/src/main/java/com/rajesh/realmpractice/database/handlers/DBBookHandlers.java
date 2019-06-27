package com.rajesh.realmpractice.database.handlers;

import android.util.Log;

import com.rajesh.realmpractice.database.tables.DbBook;
import com.rajesh.realmpractice.utils.AppController;
import com.rajesh.realmpractice.utils.DatabaseListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class DBBookHandlers {
    private static DBBookHandlers instance;

    private String TAG = "DBBookHandlers";

    public static synchronized DBBookHandlers getInstance() {
        if (instance == null) {
            instance = new DBBookHandlers();
            DBBookHandlers.getInstance();
        }
        return instance;
    }

    public void saveBook(final DbBook dbBook, final DatabaseListener<String> databaseListener) {
        AppController.getmInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(dbBook);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "dbBook list saved");
                databaseListener.success("success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "save failed: " + error.getMessage());
                databaseListener.failure("failed");
            }
        });
    }

    public void saveBookList(final List<DbBook> dbBookList, final DatabaseListener<String> databaseListener) {
        AppController.getmInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insertOrUpdate(dbBookList);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "book list saved");
                databaseListener.success("success");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "save failed: " + error.getMessage());
                databaseListener.failure("failed");
            }
        });
    }

    public void deleteAllBooks() {
        AppController.getmInstance().getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DbBook> realmResults = Realm.getDefaultInstance().where(DbBook.class).findAll();
                realmResults.deleteAllFromRealm();
            }
        });

    }

    public void deleteSpecificBook(final DbBook dbBook, final DatabaseListener<String> databaseListener) {
        /**
         * do not delete realm record in executeTransactionAsync because that will create another thread  and delete won't work
         */

        AppController.getmInstance().getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DbBook> realmResults = realm.where(DbBook.class)
                        .equalTo("bookId", dbBook.getBookId())
                        .findAll();
                if (realmResults.size() > 0) {
                    realmResults.deleteFirstFromRealm();
                    databaseListener.success("success");
                } else {
                    Log.e("handler", "no record found");
                    databaseListener.failure("failure");

                }
            }
        });


    }

    private void updateBooks() {

    }

    public List<DbBook> getBookList() {
        List<DbBook> dbBookList = Realm.getDefaultInstance().where(DbBook.class).findAll();
        if (dbBookList == null) {
            dbBookList = new ArrayList<>();
        }
        return dbBookList;
    }

    private void getSpecificBook() {

    }
}
