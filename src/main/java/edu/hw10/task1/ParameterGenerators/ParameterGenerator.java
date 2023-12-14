package edu.hw10.task1.ParameterGenerators;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;

public interface ParameterGenerator<T> {
    @NotNull
    T generate(RandomGenerator random, Annotation[] annotations);

    @SuppressWarnings("MagicNumber")
    static Map<Class<?>, ParameterGenerator<?>> getDefault() {
        return Map.ofEntries(
            Map.entry(int.class,     new IntGenerator()),
            Map.entry(Integer.class, new IntGenerator()),
            Map.entry(double.class,  new DoubleGenerator()),
            Map.entry(Double.class,  new DoubleGenerator()),
            Map.entry(boolean.class, new BooleanGenerator()),
            Map.entry(Boolean.class, new BooleanGenerator()),
            Map.entry(long.class,    new LongGenerator()),
            Map.entry(Long.class,    new LongGenerator()),
            Map.entry(String.class,  new StringGenerator(32, 128))
        );
    }
}
