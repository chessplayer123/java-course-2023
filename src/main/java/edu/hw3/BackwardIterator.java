package edu.hw3;

import java.util.Iterator;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class BackwardIterator<T> implements Iterator<T> {
    private final List<T> data;
    private int index;

    /*
    Alternative (LinkedList traversing O(n)):

    private Iterator<T> iterator;
    data = list.reversed();
    iterator = data.iterator();
     */

    public BackwardIterator(@NotNull List<T> list) {
        data = list;
        index = data.size() - 1;
    }

    @Override
    public boolean hasNext() {
        return index >= 0;
    }

    @Override
    public T next() {
        return data.get(index--);
    }
}
