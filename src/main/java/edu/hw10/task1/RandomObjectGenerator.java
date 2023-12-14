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
                return (T) method.invoke(null, randomParameters);
            } catch (UnsupportedObjectException | InvocationTargetException | IllegalAccessException exception) {
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
            throw new UnsupportedObjectException(cls, new NoSuchMethodException("Class doesn't have constructors"));
        }

        for (var constructor : constructors) {
            try {
                Object[] randomParameters = generateParameters(constructor.getParameters());
                return (T) constructor.newInstance(randomParameters);
            } catch (UnsupportedObjectException
                     | InvocationTargetException
                     | InstantiationException
                     | IllegalAccessException exception) {
                LOGGER.warn("Can't generate <{}>: {}", constructor.getName(), exception.getMessage());
            }
        }
        throw new UnsupportedObjectException(cls);
    }

    private Object[] generateParameters(Parameter[] parameters) throws UnsupportedObjectException {
        Object[] randomParameters = new Object[parameters.length];
        for (int i = 0; i < randomParameters.length; ++i) {
            if (generators.containsKey(parameters[i].getType())) {
                randomParameters[i] = generators
                    .get(parameters[i].getType())
                    .generate(random, parameters[i].getAnnotations());
            } else {
                try {
                    randomParameters[i] = nextObject(parameters[i].getType());
                } catch (UnsupportedObjectException exception) {
                    if (parameters[i].isAnnotationPresent(NotNull.class)) {
                        throw exception;
                    }
                    randomParameters[i] = null;
                }
            }
        }
        return randomParameters;
    }
}
