package edu.hw10.task1;

import edu.hw10.task1.Annotations.NotNull;
import edu.hw10.task1.ParameterGenerators.ParameterGenerator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Map;
import java.util.random.RandomGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RandomObjectGenerator {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Map<Class<?>, ParameterGenerator<?>> generators;
    private final RandomGenerator random;

    public RandomObjectGenerator(RandomGenerator random) {
        this.random = random;
        generators = ParameterGenerator.getDefault();
    }

    public RandomObjectGenerator(RandomGenerator random, Map<Class<?>, ParameterGenerator<?>> generators) {
        this.random = random;
        this.generators = generators;
    }

    public <T> T nextObject(Class<T> cls, String factoryMethodName) throws UnsupportedObjectException {
        for (Method method : cls.getDeclaredMethods()) {
            if (!method.getName().equals(factoryMethodName) || method.getReturnType() != cls) {
                continue;
            }
            try {
                Object[] randomParameters = generateParameters(method.getParameters());
                return cls.cast(method.invoke(null, randomParameters));
            } catch (Exception exception) {
                LOGGER.warn(exception.getMessage());
            }
        }
        throw new UnsupportedObjectException(
            cls,
            new NoSuchMethodException("Class doesn't have appropriate factory method")
        );
    }

    public <T> T nextObject(Class<T> cls) throws UnsupportedObjectException {
        Constructor<?>[] constructors = cls.getDeclaredConstructors();
        if (constructors.length == 0) {
            throw new UnsupportedObjectException(
                cls,
                new NoSuchMethodException("Class doesn't have public constructors")
            );
        }

        for (var constructor : constructors) {
            try {
                Object[] randomParameters = generateParameters(constructor.getParameters());
                return cls.cast(constructor.newInstance(randomParameters));
            } catch (Exception exception) {
                LOGGER.warn("Can't generate <{}>: {}", constructor.getName(), exception.getMessage());
            }
        }
        throw new UnsupportedObjectException(cls);
    }

    private Object[] generateParameters(Parameter[] parameters) throws UnsupportedObjectException {
        int length = parameters.length;
        Object[] randomParameters = new Object[length];
        for (int i = 0; i < length; ++i) {
            try {
                if (generators.containsKey(parameters[i].getType())) {
                    randomParameters[i] = generators
                        .get(parameters[i].getType())
                        .generate(random, parameters[i].getAnnotations());
                } else {
                    randomParameters[i] = nextObject(parameters[i].getType());
                }
            } catch (UnsupportedObjectException exception) {
                LOGGER.warn(exception.getMessage());
                randomParameters[i] = null;
            }
            if (parameters[i].isAnnotationPresent(NotNull.class) && randomParameters[i] == null) {
                throw new UnsupportedObjectException(parameters[i].getType());
            }
        }
        return randomParameters;
    }
}
