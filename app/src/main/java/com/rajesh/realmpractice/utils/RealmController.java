package com.rajesh.realmpractice.utils;

import io.realm.Realm;

public class RealmController {
    private final String TAG ="RealmController";
    private static RealmController realmControllerInstance;
    private final Realm realm;

    public RealmController() {
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


   /* //find all objects in the Book.class
    public RealmResults<Book> getBooks() {
        return realm.where(Book.class).findAll();
    }

    //query a single item with the given id
    public Book getBook(String id) {

        return realm.where(Book.class).equalTo("id", id).findFirst();
    }

    //check if Book.class is empty
    public boolean hasBooks() {

        return !realm.allObjects(Book.class).isEmpty();
    }

    //query example
    public RealmResults<Book> queryedBooks() {

        return realm.where(Book.class)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll();

    }*/
}
