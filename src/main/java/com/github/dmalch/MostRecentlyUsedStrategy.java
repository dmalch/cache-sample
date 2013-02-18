package com.github.dmalch;

public class MostRecentlyUsedStrategy implements Strategy {
    @Override
    public int findElementToEvict() {
        return 0;
    }

    @Override
    public void onGet(final int key) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onAdd(final int key) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onRemove(final int key) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
