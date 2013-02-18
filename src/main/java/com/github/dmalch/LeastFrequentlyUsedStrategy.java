package com.github.dmalch;

import org.apache.commons.lang.ObjectUtils;

import java.util.PriorityQueue;

public class LeastFrequentlyUsedStrategy implements Strategy {
    private final PriorityQueue<UseCountEntry> mostRecentlyUsedElements = new PriorityQueue<UseCountEntry>(2);

    @Override
    public int findElementToEvict() {
        return mostRecentlyUsedElements.peek().getKey();
    }

    @Override
    public void onGet(final int key) {
        UseCountEntry element = findElement(key);

        element.incrementCount();
        mostRecentlyUsedElements.remove(element);
        mostRecentlyUsedElements.offer(element);
    }

    private UseCountEntry findElement(final int key) {
        UseCountEntry element = null;
        for (UseCountEntry entry : mostRecentlyUsedElements) {
            if (ObjectUtils.equals(entry.getKey(), key)) {
                element = entry;
                break;
            }
        }
        return element;
    }

    @Override
    public void onAdd(final int key) {
        mostRecentlyUsedElements.offer(entry(key, 0));
    }

    @Override
    public void onRemove(final int key) {
        UseCountEntry element = findElement(key);
        mostRecentlyUsedElements.remove(element);
    }

    private UseCountEntry entry(final int key, final int count) {
        return new UseCountEntry(key, count);
    }
}
