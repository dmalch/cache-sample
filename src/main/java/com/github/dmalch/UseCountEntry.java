package com.github.dmalch;

public class UseCountEntry implements Comparable<UseCountEntry> {
    private final Integer key;
    private Integer count;

    public UseCountEntry(final int key, final int count) {
        this.key = key;
        this.count = count;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof UseCountEntry)) return false;

        final UseCountEntry that = (UseCountEntry) o;

        if (count != null ? !count.equals(that.count) : that.count != null) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(final UseCountEntry o) {
        final int keyCmp = key.compareTo(o.key);
        if (keyCmp == 0) {
            return keyCmp;
        }

        final int countCmp = count.compareTo(o.count);
        if (countCmp != 0) {
            return countCmp;
        }

        return keyCmp;
    }

    public Integer getKey() {
        return key;
    }

    public void incrementCount() {
        count++;
    }
}
