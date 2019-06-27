package com.rajesh.realmpractice.utils;

import io.realm.Realm;

public class RealmController {
    private final String TAG ="RealmController";
    private static RealmController realmControllerInstance;
    private final Realm realm;

    private RealmController() {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController getRealmControllerInstance() {
        if (realmControllerInstance == null) {
            realmControllerInstance = new RealmController();
        }
        return realmControllerInstance;
    }

    public Realm getRealm() {
        return realm;
    }

    //Refresh the realm istance
    public void refresh() {
        realm.refresh();
    }


   /* //find all objects in the DbBook.class
    public RealmResults<DbBook> getBooks() {
        return realm.where(DbBook.class).findAll();
    }

    //query a single item with the given id
    public DbBook getBook(String id) {

        return realm.where(DbBook.class).equalTo("id", id).findFirst();
    }

    //check if DbBook.class is empty
    public boolean hasBooks() {

        return !realm.allObjects(DbBook.class).isEmpty();
    }

    //query example
    public RealmResults<DbBook> queryedBooks() {

        return realm.where(DbBook.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }*/
}
