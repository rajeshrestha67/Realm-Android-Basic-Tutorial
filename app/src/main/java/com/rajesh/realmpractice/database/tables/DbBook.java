package com.rajesh.realmpractice.database.tables;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class DbBook extends RealmObject implements Serializable {
    @PrimaryKey
    private int bookId;
    private String name;
    private String price;
    private String author;
    private String published;

    public DbBook(){

    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublishYear() {
        return published;
    }

    public void setPublishYear(String publishYear) {
        this.published = publishYear;
    }
}
