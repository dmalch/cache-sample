package com.github.dmalch;

public interface StorageListener {
    void onGet(int key);

    void onAdd(int key);

    void onRemove(int key);
}
