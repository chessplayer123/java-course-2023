package edu.hw3;

import java.util.TreeMap;

public final class NullTreeMapProducer {
    private NullTreeMapProducer() {
    }

    public static <K extends Comparable<K>, V> TreeMap<K, V> getNullTreeMap() {
        return new TreeMap<K, V>((key1, key2) -> {
            if (key1 == null && key2 == null) {
                return 0;
            } else if (key1 == null) {
                return -1;
            } else if (key2 == null) {
                return 1;
            }
            return key1.compareTo(key2);
        });
    }
}
