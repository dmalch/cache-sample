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
        final Cache cache = givenEmptyLFUCacheWith(sourceWith(expectedKey, expectedValue));

        whenAddElementIntoCache(cache, expectedKey);

        thenCacheContainsElement(cache, expectedKey, expectedValue);
    }

    @Test
    public void testWhenCacheCapacityIsExceededThenElementsAreEvictedLFU() throws Exception {
        final int firstExpectedKey = givenExpectedKey();
        final int firstExpectedValue = givenExpectedValue();
        final int secondExpectedKey = givenExpectedKey();
        final int secondExpectedValue = givenExpectedValue();
        final int thirdExpectedKey = givenExpectedKey();
        final int thirdExpectedValue = givenExpectedValue();
        final Cache cache = givenEmptyLFUCacheWith(sourceWith(firstExpectedKey, firstExpectedValue, secondExpectedKey, secondExpectedValue, thirdExpectedKey, thirdExpectedValue));

        whenAddElementIntoCache(cache, firstExpectedKey);
        whenAddElementIntoCache(cache, secondExpectedKey);
        useElement(cache, secondExpectedKey);
        whenAddElementIntoCache(cache, thirdExpectedKey);

        thenCacheContainsElement(cache, thirdExpectedKey, thirdExpectedValue);
        thenCacheContainsElement(cache, secondExpectedKey, secondExpectedValue);
        thenCacheDoesNotContainElement(cache, firstExpectedKey);
    }

    private void useElement(final Cache cache, final int secondExpectedKey) {
        whenAddElementIntoCache(cache, secondExpectedKey);
    }

    @Test
    public void testWhenCacheCapacityIsExceededThenElementsAreEvictedMRU() throws Exception {
        final int firstExpectedKey = givenExpectedKey();
        final int firstExpectedValue = givenExpectedValue();
        final int secondExpectedKey = givenExpectedKey();
        final int secondExpectedValue = givenExpectedValue();
        final int thirdExpectedKey = givenExpectedKey();
        final int thirdExpectedValue = givenExpectedValue();
        final Cache cache = givenEmptyMRUCacheWith(sourceWith(firstExpectedKey, firstExpectedValue, secondExpectedKey, secondExpectedValue, thirdExpectedKey, thirdExpectedValue));

        whenAddElementIntoCache(cache, firstExpectedKey);
        whenAddElementIntoCache(cache, secondExpectedKey);
        useElement(cache, secondExpectedKey);
        whenAddElementIntoCache(cache, thirdExpectedKey);

        thenCacheContainsElement(cache, thirdExpectedKey, thirdExpectedValue);
        thenCacheContainsElement(cache, firstExpectedKey, firstExpectedValue);
        thenCacheDoesNotContainElement(cache, secondExpectedKey);
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

    private Cache givenEmptyMRUCacheWith(final DataSource dataSource) {
        return new CacheImpl(2, dataSource, new HashMapStorage(), new MostRecentlyUsedStrategy());
    }

    private Cache givenEmptyLFUCacheWith(final DataSource dataSource) {
        return new CacheImpl(2, dataSource, new HashMapStorage(), new LeastFrequentlyUsedStrategy());
    }

    private DataSource sourceWith(final int expectedKey, final int expectedValue) {

        final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(expectedKey, expectedValue);

        return dataSourceFor(map);
    }

    private DataSource sourceWith(final int firstExpectedKey, final int firstExpectedValue,
                                  final int secondExpectedKey, final int secondExpectedValue,
                                  final int thirdExpectedKey, final int thirdExpectedValue) {

        final Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(firstExpectedKey, firstExpectedValue);
        map.put(secondExpectedKey, secondExpectedValue);
        map.put(thirdExpectedKey, thirdExpectedValue);

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
