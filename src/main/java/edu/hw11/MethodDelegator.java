package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class MethodDelegator {
    private MethodDelegator() {
    }

    public static void delegate(Class<?> src, Class<?> target, String methodName) {
        new ByteBuddy()
            .redefine(src)
            .method(named(methodName))
            .intercept(MethodDelegation.to(target))
            .make()
            .load(src.getClassLoader(), ClassReloadingStrategy.fromInstalledAgent());
    }
}
