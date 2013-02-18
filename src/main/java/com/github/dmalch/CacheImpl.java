package com.github.dmalch;

public class CacheImpl implements Cache {
    private int maxSize;
    private DataSource dataSource;
    private Storage storage;
    private Strategy strategy;

    public CacheImpl(final int maxSize, final DataSource dataSource, final Storage storage, final Strategy strategy) {
        this.maxSize = maxSize;
        this.dataSource = dataSource;
        this.storage = storage;
        this.strategy = strategy;
        storage.addListener(strategy);
    }

    @Override
    public int get(final int key) {
        if (storage.contains(key)) {
            return storage.get(key);
        } else {
            if (storage.size() >= maxSize) {
                storage.remove(strategy.findElementToEvict());
            }
            final int value = dataSource.get(key);
            storage.add(key, value);
            return value;
        }
    }
}
