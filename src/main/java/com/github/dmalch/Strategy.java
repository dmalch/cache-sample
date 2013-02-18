package com.github.dmalch;

public interface Strategy extends StorageListener {
    int findElementToEvict();
}
