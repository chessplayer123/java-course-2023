package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import static net.bytebuddy.matcher.ElementMatchers.named;

public final class ClassWithToStringGenerator {
    private ClassWithToStringGenerator() {
    }

    public static Class<?> generate() {
        return new ByteBuddy()
            .subclass(Object.class)
            .method(named("toString")).intercept(FixedValue.value("Hello, ByteBuddy!"))
            .make()
            .load(ClassWithToStringGenerator.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();
    }
}
