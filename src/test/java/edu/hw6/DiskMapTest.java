package edu.hw6;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.util.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class DiskMapTest {
    @Test
    void mapFromExistingDirectoryDoesNotThrowsException(@TempDir Path tempDir) {
        assertThatCode(() -> new DiskMap(tempDir))
            .doesNotThrowAnyException();
    }

    @Test
    void mapFromNotExistingDirectoryCreateNewDirectory(@TempDir Path tempDir) {
        Path pathToDirectory = tempDir.resolve("diskMap");
        assertThatCode(() -> new DiskMap(pathToDirectory))
            .doesNotThrowAnyException();
        assertThat(pathToDirectory.toFile()).isDirectory();
    }

    @Test
    void mapFromFileThrowsException(@TempDir Path tempDir) {
        Path pathToFile = tempDir.resolve("test.txt");
        Files.newFile(pathToFile.toString());

        assertThatThrownBy(() -> new DiskMap(pathToFile))
            .isInstanceOf(IOException.class)
            .hasMessage("path must lead to directory");
    }

    @Test
    void putNewKeyValue(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String key = "key";
        String value = "value";

        map.put(key, value);
        String actualValue = map.get(key);

        assertThat(actualValue).isEqualTo(value);
    }

    @Test
    void putReturnPreviousValueOfKey(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String key = "sameKey";
        String prevValue = "prevValue";
        String newValue = "newValue";

        map.put(key, prevValue);
        String actualPrevValue = map.put(key, newValue);

        assertThat(actualPrevValue).isEqualTo(prevValue);
    }

    @Test
    void putStringsWithSameHashCode(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String key1 = "Aa"; // hash = 2112
        String value1 = "value1";

        String key2 = "BB"; // hash = 2112
        String value2 = "value2";

        map.put(key1, value1);
        map.put(key2, value2);

        assertThat(map)
            .containsEntry(key1, value1)
            .containsEntry(key2, value2);
    }

    @Test
    void removeExistingKey(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String key = "entry's key";
        String value = "123454321";

        map.put(key, value);
        String prevValue = map.remove(key);

        assertThat(map).doesNotContainKey(key);
        assertThat(prevValue).isEqualTo(value);
    }

    @Test
    void containsKeyReturnTrueIfKeyIsPresent(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String key = "entry's key";
        String value = "value";

        map.put(key, value);

        assertThat(map.containsKey(key)).isTrue();
    }

    @Test
    void containsKeyReturnFalseIfKeyIsNotPresent(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String absentKey = "absent";
        String presentKey = "present";

        map.put(presentKey, "value");

        assertThat(map.containsKey(absentKey)).isFalse();
    }

    @Test
    void containsValueReturnTrueIfValueIsPresent(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String key = "entry's key";
        String value = "value";

        map.put(key, value);

        assertThat(map.containsValue(value)).isTrue();
    }

    @Test
    void containsValueReturnFalseIfValueIsNotPresent(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String value = "random value";

        assertThat(map.containsValue(value)).isFalse();
    }

    @Test
    void sizeReturnCorrectDiskMapSize(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        int actualSize = map.size();
        int expectedSize = 3;

        assertThat(actualSize).isEqualTo(expectedSize);
    }

    @Test
    void clearMapDoesRemoveAllEntries(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        map.put("rand_key1", "rand_value1");
        map.put("rand_key2", "rand_value2");

        map.clear();

        int actualSize = map.size();

        assertThat(actualSize).isZero();
    }

    @Test
    void valuesReturnsAllPresentValues(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        String[] expectedValues = new String[] {"value1", "val2", "1 2 3 4", "\"value\""};
        for (String value : expectedValues) {
            map.put("key for " + value, value);
        }
        Collection<String> actualValues = map.values();

        assertThat(actualValues).containsOnly(expectedValues);
    }

    @Test
    void keySetReturnsAllPresentKeys(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        Set<String> expectedKeys = Set.of("key1", "key2", "1234321", "'1''1'");
        for (String key : expectedKeys) {
            map.put(key, "same random value");
        }
        Set<String> actualKeys = map.keySet();

        assertThat(actualKeys).isEqualTo(expectedKeys);
    }

    @Test
    void entrySetReturnsAllPresentEntries(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        Map<String, String> insertedMap = Map.of(
            "key1", "value for key1",
            "123", "321",
            "", "empty key"
        );

        map.putAll(insertedMap);

        Set<Map.Entry<String, String>> actualEntries = map.entrySet();
        Set<Map.Entry<String, String>> expectedEntries = insertedMap.entrySet();

        assertThat(actualEntries).isEqualTo(expectedEntries);
    }

    @Test
    void putInMapWithReadonlyDirDoesNotChangeMap(@TempDir Path tempDir) throws IOException {
        DiskMap map = new DiskMap(tempDir);

        tempDir.toFile().setReadOnly();

        map.put("key", "value");

        assertThat(map.isEmpty()).isTrue();
    }
}
