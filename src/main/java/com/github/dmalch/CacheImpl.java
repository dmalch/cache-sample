package com.github.dmalch;

public class CacheImpl implements Cache {
    private DataSource dataSource;
    private Storage storage;

    public CacheImpl(final DataSource dataSource, final Storage storage) {
        this.dataSource = dataSource;
        this.storage = storage;
    }

    @Override
    public int get(final int key) {
        if (storage.contains(key)) {
            return storage.get(key);
        } else {
            final int value = dataSource.get(key);
            storage.add(key, value);
            return value;
        }
    }
}
