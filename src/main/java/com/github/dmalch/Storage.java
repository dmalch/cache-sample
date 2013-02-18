package com.github.dmalch;

public interface Storage {
    boolean contains(int key);

    int get(int key);

    void add(int key, int value);
}
