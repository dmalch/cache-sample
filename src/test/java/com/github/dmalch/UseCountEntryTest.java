package com.github.dmalch;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UseCountEntryTest {
    @Test
    public void testCompareElementsWithDifferentCounts() throws Exception {
        final UseCountEntry first = new UseCountEntry(1, 0);
        final UseCountEntry second = new UseCountEntry(1, 1);

        assertThat(second, comparesEqualTo(first));
    }

    @Test
    public void testCompareElementsWithEqualCountsButDifferentKeys() throws Exception {
        final UseCountEntry first = new UseCountEntry(0, 1);
        final UseCountEntry second = new UseCountEntry(1, 1);

        assertThat(first, lessThan(second));
        assertThat(second, greaterThan(first));
    }

    @Test
    public void testCompareElementsWithEqualCountsEndEqualKeys() throws Exception {
        final UseCountEntry first = new UseCountEntry(1, 1);
        final UseCountEntry second = new UseCountEntry(1, 1);

        assertThat(second, comparesEqualTo(first));
    }

    @Test
    public void testCompareElementsWithDifferentCountsAreNotEqual() throws Exception {
        final UseCountEntry first = new UseCountEntry(1, 0);
        final UseCountEntry second = new UseCountEntry(1, 1);

        assertThat(first, not(equalTo(second)));
    }

    @Test
    public void testCompareElementsWithEqualCountsButDifferentKeysAreNotEqual() throws Exception {
        final UseCountEntry first = new UseCountEntry(0, 1);
        final UseCountEntry second = new UseCountEntry(1, 1);

        assertThat(first, not(equalTo(second)));
    }

    @Test
    public void testCompareElementsWithEqualCountsEndEqualKeysAreEqual() throws Exception {
        final UseCountEntry first = new UseCountEntry(1, 1);
        final UseCountEntry second = new UseCountEntry(1, 1);

        assertThat(first, equalTo(second));
    }
}
