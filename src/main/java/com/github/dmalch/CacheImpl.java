package com.github.dmalch;

public class CacheImpl implements Cache {
    private int maxSize;
    private DataSource dataSource;
    private Storage storage;

    public CacheImpl(final int maxSize, final DataSource dataSource, final Storage storage) {
        this.maxSize = maxSize;
        this.dataSource = dataSource;
        this.storage = storage;
    }

    @Override
    public int get(final int key) {
        if (storage.contains(key)) {
            return storage.get(key);
        } else {
            if (storage.size() >= maxSize) {
                storage.remove(0);
            }
            final int value = dataSource.get(key);
            storage.add(key, value);
            return value;
        }
    }
}
