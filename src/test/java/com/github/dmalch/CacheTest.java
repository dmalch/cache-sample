package com.github.dmalch;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class CacheTest {
    @Test
    public void testWhenUserAddsElementIntoCacheThenItIsPlacedInCache() throws Exception {
        final int expectedKey = givenExpectedKey();
        final int expectedValue = givenExpectedValue();
        final Cache cache = givenEmptyCacheWith(sourceWith(expectedKey, expectedValue));

        whenAddElementIntoCache(cache, expectedKey);

        thenCacheContainsElement(cache, expectedKey, expectedValue);
    }

    @Test
    public void testWhenCacheCapacityIsExceededThenElementsAreEvicted() throws Exception {
        final int firstExpectedKey = givenExpectedKey();
        final int firstExpectedValue = givenExpectedValue();
        final int secondExpectedKey = givenExpectedKey();
        final int secondExpectedValue = givenExpectedValue();
        final Cache cache = givenEmptyCacheWith(sourceWith(firstExpectedKey, firstExpectedValue, secondExpectedKey, secondExpectedValue));

        whenAddElementIntoCache(cache, firstExpectedKey);
        whenAddElementIntoCache(cache, secondExpectedKey);

        thenCacheContainsElement(cache, secondExpectedKey, secondExpectedValue);
        thenCacheDoesNotContainElement(cache, firstExpectedKey);
    }

    private void thenCacheDoesNotContainElement(final Cache cache, final int expectedKey) {
        assertThat(cache.get(expectedKey), equalTo(0));
    }

    private void thenCacheContainsElement(final Cache cache, final int expectedKey, final int expectedValue) {
        assertThat(cache.get(expectedKey), equalTo(expectedValue));
    }

    private int whenAddElementIntoCache(final Cache cache, final int key) {
        return cache.get(key);
    }

    private Cache givenEmptyCacheWith(final DataSource dataSource) {
        return new CacheImpl(1, dataSource, new HashMapStorage());
    }

    private DataSource sourceWith(final int expectedKey, final int expectedValue) {

        final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(expectedKey, expectedValue);

        return dataSourceFor(map);
    }

    private DataSource sourceWith(final int firstExpectedKey, final int firstExpectedValue, final int secondExpectedKey, final int secondExpectedValue) {

        final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(firstExpectedKey, firstExpectedValue);
        map.put(secondExpectedKey, secondExpectedValue);

        return dataSourceFor(map);
    }

    private DataSource dataSourceFor(final Map<Integer, Integer> map) {
        return new DataSource() {
            private int invocationCount = 0;

            @Override
            public int get(final int key) {
                if (!invocationsExceeded()) {
                    return map.get(key);
                } else {
                    return 0;
                }
            }

            private boolean invocationsExceeded() {
                return invocationCount++ >= map.size();
            }
        };
    }

    private int givenExpectedKey() {
        return RandomUtils.nextInt();
    }

    private int givenExpectedValue() {
        return RandomUtils.nextInt();
    }
}
