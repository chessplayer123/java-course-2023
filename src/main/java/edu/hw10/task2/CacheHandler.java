package edu.hw10.task2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CacheHandler<T> implements InvocationHandler {
    private final Map<String, Map<List<Object>, Object>> interfaceCache;
    private final T resource;
    private final Path cachePath;

    private CacheHandler(T resource, Path cachePath) {
        interfaceCache = new HashMap<>();
        this.resource = resource;
        this.cachePath = cachePath;
    }

    public static <T> CacheHandler<T> of(T resource, Path cachePath) {
        return new CacheHandler<T>(resource, cachePath);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
        if (!method.isAnnotationPresent(Cache.class)) {
            return method.invoke(resource, objects);
        }

        List<Object> args = List.of(objects);
        var methodCache = interfaceCache.computeIfAbsent(method.getName(), key -> new HashMap<>());
        if (methodCache.containsKey(args)) {
            return methodCache.get(args);
        }

        Object result = method.invoke(resource, objects);
        methodCache.put(args, result);

        boolean persist = method.getAnnotation(Cache.class).persist();
        if (persist) {
            String filename = "%s(%s).txt".formatted(
                method.getName(),
                args.stream().map(Object::toString).collect(Collectors.joining(", "))
            );
            Files.writeString(cachePath.resolve(filename), result.toString());
        }
        return result;
    }
}
