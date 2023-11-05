package edu.hw3;

import com.sun.source.tree.Tree;
import org.junit.jupiter.api.Test;
import java.util.TreeMap;
import static org.assertj.core.api.Assertions.assertThat;

public class NullTreeMapTest {
    @Test
    void putNullKeyInTreeMap() {
        TreeMap<String, String> tree = NullTreeMapProducer.getNullTreeMap();

        tree.put(null, "value");

        assertThat(tree.containsKey(null)).isTrue();
    }

    @Test
    void valueInNullKeyIsCorrect() {
        TreeMap<String, String> tree = NullTreeMapProducer.getNullTreeMap();

        String value = "test";
        tree.put(null, value);

        assertThat(tree.get(null)).isEqualTo(value);
    }

    @Test
    void reassignNullKey() {
        TreeMap<String, String> tree = NullTreeMapProducer.getNullTreeMap();

        tree.put(null, "value");
        tree.put(null, "new value");

        assertThat(tree.get(null)).isEqualTo("new value");
    }

    @Test
    void putNullAndNotNullKeys() {
        TreeMap<String, String> tree = NullTreeMapProducer.getNullTreeMap();

        tree.put(null, "value1");
        tree.put("not null key", "value2");
        tree.put("another not null key", "value3");

        assertThat(tree.get(null)).isEqualTo("value1");
        assertThat(tree.get("not null key")).isEqualTo("value2");
        assertThat(tree.get("another not null key")).isEqualTo("value3");
    }

    @Test
    void putNotNullKeys() {
        TreeMap<Integer, Integer> nullTree = NullTreeMapProducer.getNullTreeMap();
        TreeMap<Integer, Integer> tree = new TreeMap<>();

        tree.put(2, 71);
        nullTree.put(2, 71);

        tree.put(3, 14);
        nullTree.put(3, 14);

        assertThat(nullTree).isEqualTo(tree);
    }
}
