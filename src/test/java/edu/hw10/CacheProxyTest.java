package edu.hw10;

import edu.hw10.task2.Cache;
import edu.hw10.task2.CacheProxy;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class CacheProxyTest {
    @Test
    void methodsWithCacheAnnotation_shouldBeEvaluatedOnce() {
        CachedIncrementer resource = counter -> counter.count++;
        CachedIncrementer proxy = CacheProxy.create(resource, null);

        AccessCounter expectedValue = new AccessCounter();
        AccessCounter cachedValue = new AccessCounter();
        AccessCounter actualValue = new AccessCounter();

        proxy.access(cachedValue); // cache
        proxy.access(actualValue); // retrieve from cache

        assertThat(cachedValue.count).isOne();
        assertThat(actualValue.count)
            .isZero()
            .isEqualTo(expectedValue.count);
    }

    @Test
    void methodsWithoutCacheAnnotation_shouldNotBeCached() {
        Incrementer resource = counter -> counter.count++;
        Incrementer proxy = CacheProxy.create(resource, null);

        AccessCounter cachedValue = new AccessCounter();
        AccessCounter actualValue = new AccessCounter();

        proxy.access(cachedValue);
        proxy.access(actualValue);

        assertThat(cachedValue.count)
            .isEqualTo(actualValue.count)
            .isOne();
    }

    @Test
    void cacheWithPersistFlag_shouldCreateFileInGivenDirectory(@TempDir Path tempDir) {
        InterfaceWithPersistFlag resource = strings -> String.join("", strings);
        InterfaceWithPersistFlag proxy = CacheProxy.create(resource, tempDir);

        List<String> argument = List.of("h", "e", "l", "l", "o");
        File expectedFile = tempDir.resolve("concat([h, e, l, l, o]).txt").toFile();
        String expectedContent = "hello";

        proxy.concat(argument);

        assertThat(expectedFile)
            .isFile()
            .hasContent(expectedContent);
    }

    @Test
    void cacheWithPersistFlag_shouldCreateFilesForAllCachedMethods(@TempDir Path tempDir) {
        InterfaceWithTwoCachedMethods resource = new InterfaceWithTwoCachedMethods() {
            @Override
            public String concat(List<String> strings) {
                return String.join("", strings);
            }

            @Override
            public int sum(int a, int b) {
                return a + b;
            }
        };
        InterfaceWithTwoCachedMethods proxy = CacheProxy.create(resource, tempDir);

        proxy.concat(List.of("h", "e", "l", "l", "o"));
        proxy.sum(90, 1);

        File expectedFile1 = tempDir.resolve("concat([h, e, l, l, o]).txt").toFile();
        String content1 = "hello";

        File expectedFile2 = tempDir.resolve("sum(90, 1).txt").toFile();
        String content2 = "91";

        assertThat(expectedFile1)
            .isFile()
            .hasContent(content1);
        assertThat(expectedFile2)
            .isFile()
            .hasContent(content2);
    }

    @Test
    void cacheWithoutPersistFlag_shouldNotCreateFileInGivenDirectory(@TempDir Path tempDir) {
        InterfaceWithoutPersistFlag resource = num -> (long) num * num;
        InterfaceWithoutPersistFlag proxy = CacheProxy.create(resource, tempDir);

        proxy.func(10);
        proxy.func(0);

        assertThat(tempDir).isEmptyDirectory();
    }

    public interface InterfaceWithTwoCachedMethods {
        @Cache(persist = true)
        String concat(List<String> strings);

        @Cache(persist = true)
        int sum(int a, int b);
    }

    public interface InterfaceWithPersistFlag {
        @Cache(persist = true)
        String concat(List<String> strings);
    }

    public interface InterfaceWithoutPersistFlag {
        @Cache
        long func(int num);
    }

    public static class AccessCounter {
        public int count = 0;

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            return obj != null && obj.getClass() == AccessCounter.class;
        }
    }

    public interface CachedIncrementer {
        @Cache
        void access(AccessCounter counter);
    }

    public interface Incrementer {
        void access(AccessCounter counter);
    }
}
