package edu.hw10.task1.ParameterGenerators;

import java.lang.annotation.Annotation;
import java.util.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;

public class BooleanGenerator implements ParameterGenerator<Boolean> {
    @Override
    public @NotNull Boolean generate(RandomGenerator random, Annotation[] annotations) {
        return random.nextBoolean();
    }
}
