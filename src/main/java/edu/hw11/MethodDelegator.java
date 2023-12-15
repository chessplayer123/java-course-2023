package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class MethodDelegator {
    private MethodDelegator() {
    }

    public static <T> T delegate(Class<T> src, Class<?> target, String methodName)
        throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return new ByteBuddy()
            .subclass(src)
            .method(named(methodName))
            .intercept(MethodDelegation.to(target))
            .make()
            .load(src.getClassLoader())
            .getLoaded()
            .getConstructor()
            .newInstance();
    }
}
