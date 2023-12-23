package edu.hw10.task2;

import java.lang.reflect.Proxy;
import java.nio.file.Path;

public final class CacheProxy {
    private CacheProxy() {
    }

    public static <T> T create(T resource, Path cachePath) {
        Class<?> cls = resource.getClass();
        return (T) Proxy.newProxyInstance(
            cls.getClassLoader(),
            cls.getInterfaces(),
            CacheHandler.of(resource, cachePath)
        );
    }
}
