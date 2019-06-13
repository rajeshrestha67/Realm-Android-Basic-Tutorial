package com.rajesh.realmpractice.utils;

public interface DatabaseListener<T> {

    void success(T response);

    void failure(T response);
}
