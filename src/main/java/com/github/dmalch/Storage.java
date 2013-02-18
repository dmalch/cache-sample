package com.github.dmalch;

public interface Storage {
    boolean contains(int key);

    int get(int key);

    void add(int key, int value);

    int size();

    void remove(int key);

    void addListener(StorageListener storageListener);
}
