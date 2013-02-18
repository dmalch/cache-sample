package com.github.dmalch;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class LeastFrequentlyUsedStrategyTest {
    @Test
    public void test1() throws Exception {
        final LeastFrequentlyUsedStrategy leastFrequentlyUsedStrategy = givenStrategy();
        final int expectedElementToEvict = 5;

        whenAddElement(leastFrequentlyUsedStrategy, expectedElementToEvict);
        final int actualElement = whenFindElementToEvict(leastFrequentlyUsedStrategy);

        assertThat(actualElement, equalTo(expectedElementToEvict));
    }

    @Test
    public void test2() throws Exception {
        final LeastFrequentlyUsedStrategy leastFrequentlyUsedStrategy = givenStrategy();
        final int expectedElementToEvict = 5;

        whenAddElement(leastFrequentlyUsedStrategy, 4);
        whenAddElement(leastFrequentlyUsedStrategy, expectedElementToEvict);
        whenAccessElement(leastFrequentlyUsedStrategy, 4);
        final int actualElement = whenFindElementToEvict(leastFrequentlyUsedStrategy);

        assertThat(actualElement, equalTo(expectedElementToEvict));
    }

    @Test
    public void test3() throws Exception {
        final LeastFrequentlyUsedStrategy leastFrequentlyUsedStrategy = givenStrategy();
        final int expectedElementToEvict = 5;

        whenAddElement(leastFrequentlyUsedStrategy, 6);
        whenAddElement(leastFrequentlyUsedStrategy, expectedElementToEvict);
        whenAccessElement(leastFrequentlyUsedStrategy, 6);
        final int actualElement = whenFindElementToEvict(leastFrequentlyUsedStrategy);

        assertThat(actualElement, equalTo(expectedElementToEvict));
    }

    private void whenAccessElement(final LeastFrequentlyUsedStrategy leastFrequentlyUsedStrategy, final int expectedElementToEvict) {
        leastFrequentlyUsedStrategy.onGet(expectedElementToEvict);
    }

    private int whenFindElementToEvict(final LeastFrequentlyUsedStrategy leastFrequentlyUsedStrategy) {
        return leastFrequentlyUsedStrategy.findElementToEvict();
    }

    private void whenAddElement(final LeastFrequentlyUsedStrategy leastFrequentlyUsedStrategy, final int expectedElementToEvict) {
        leastFrequentlyUsedStrategy.onAdd(expectedElementToEvict);
    }

    private LeastFrequentlyUsedStrategy givenStrategy() {
        return new LeastFrequentlyUsedStrategy();
    }
}
