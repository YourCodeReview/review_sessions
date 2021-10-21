package ru.hawoline.alonar.util;

import java.io.Serializable;

public class Pair<T, V> implements Serializable {
    private T mFirst;
    private V mSecond;

    private static final long serialVersionUID = 7005557444853977271L;

    public Pair(T first, V second) {
        mFirst = first;
        mSecond = second;
    }

    public T getFirst() {
        return mFirst;
    }

    public void setFirst(T first) {
        mFirst = first;
    }

    public V getSecond() {
        return mSecond;
    }

    public void setSecond(V second) {
        mSecond = second;
    }
}
