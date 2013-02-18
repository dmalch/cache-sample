package com.github.dmalch;

import java.util.HashMap;

public class LinkedListStorage implements Storage {

    private final HashMap<Integer, Integer> table = new HashMap<Integer, Integer>();

    @Override
    public boolean contains(final int key) {
        return table.containsKey(key);
    }

    @Override
    public int get(final int key) {
        return table.get(key);
    }

    @Override
    public void add(final int key, final int value) {
        table.put(key, value);
    }
}
