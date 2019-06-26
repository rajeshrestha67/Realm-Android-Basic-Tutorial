package com.rajesh.realmpractice.database.handlers;

import android.util.Log;

import com.rajesh.realmpractice.database.tables.DbBook;
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
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
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
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
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

        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DbBook> realmResults = Realm.getDefaultInstance().where(DbBook.class).findAll();
                realmResults.deleteAllFromRealm();
            }
        });

    }

    public void deleteSpecificBook(final DbBook dbBook, final DatabaseListener<String> databaseListener) {
        Realm.getDefaultInstance().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<DbBook> realmResults = Realm.getDefaultInstance().where(DbBook.class).findAll();
                DbBook dbBookObj = realmResults.where().equalTo("bookId", dbBook.getBookId()).findFirst();
                if (dbBookObj != null) {
                    if (!realm.isInTransaction()) {
                        realm.beginTransaction();
                    }
                    dbBookObj.deleteFromRealm();
                    realm.commitTransaction();
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                databaseListener.success("successfully deleted record");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e(TAG, "error: " + error.getMessage());
                databaseListener.failure("cannot delete record : " + error.getMessage());

            }
        });


    }

    private void updateBooks() {

    }

    public List<DbBook> getBookList() {
//        List<DbBook> dbBookList = Realm.getDefaultInstance().where(DbBook.class).equalTo("fieldId", "value").findAllAsync();
        List<DbBook> dbBookList = Realm.getDefaultInstance().where(DbBook.class).findAll();
        if (dbBookList == null) {
            dbBookList = new ArrayList<>();
        }
        return dbBookList;
    }

    private void getSpecificBook() {

    }
}
