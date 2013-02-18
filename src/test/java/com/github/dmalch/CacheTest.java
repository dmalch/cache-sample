package com.github.dmalch;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

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

    private void thenCacheContainsElement(final Cache cache, final int expectedKey, final int expectedValue) {
        assertThat(cache.get(expectedKey), equalTo(expectedValue));
    }

    private int whenAddElementIntoCache(final Cache cache, final int key) {
        return cache.get(key);
    }

    private Cache givenEmptyCacheWith(final DataSource dataSource) {
        return new CacheImpl(dataSource, new HashMapStorage());
    }

    private DataSource sourceWith(final int expectedKey, final int expectedValue) {
        return new DataSource() {
            public int invocationCount = 0;

            @Override
            public int get(final int key) {
                if (key == expectedKey && firstInvocation()) {
                    return expectedValue;
                }
                return 0;
            }

            private boolean firstInvocation() {
                return invocationCount++ == 0;
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
