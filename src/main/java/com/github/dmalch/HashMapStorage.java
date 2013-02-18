package com.github.dmalch;

import java.util.HashMap;

public class HashMapStorage implements Storage {

    private final HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();
    private StorageListener storageListener;

    @Override
    public boolean contains(final int key) {
        return table.containsKey(key);
    }

    @Override
    public int get(final int key) {
        storageListener.onGet(key);
        return table.get(key);
    }

    @Override
    public void add(final int key, final int value) {
        storageListener.onAdd(key);
        table.put(key, value);
    }

    @Override
    public int size() {
        return table.size();
    }

    @Override
    public void remove(final int key) {
        storageListener.onRemove(key);
        table.remove(key);
    }

    @Override
    public void addListener(final StorageListener storageListener) {
        this.storageListener = storageListener;
    }
}
