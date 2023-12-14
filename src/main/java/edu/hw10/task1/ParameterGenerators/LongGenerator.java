package edu.hw10.task1.ParameterGenerators;

import edu.hw10.task1.Annotations.Max;
import edu.hw10.task1.Annotations.Min;
import java.lang.annotation.Annotation;
import java.util.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;

public class LongGenerator implements ParameterGenerator<Long> {
    @Override
    @NotNull
    public Long generate(RandomGenerator random, Annotation[] annotations) {
        long minValue = Long.MIN_VALUE;
        long maxValue = Long.MAX_VALUE - 1;
        for (var annotation : annotations) {
            if (annotation instanceof Min minAnnotation) {
                minValue = minAnnotation.value();
            } else if (annotation instanceof Max maxAnnotation) {
                maxValue = maxAnnotation.value();
            }
        }
        return random.nextLong(minValue, maxValue + 1);
    }
}
