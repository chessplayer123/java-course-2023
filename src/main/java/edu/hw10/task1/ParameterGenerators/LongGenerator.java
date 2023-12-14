package edu.hw10.task1.ParameterGenerators;

import edu.hw10.task1.Annotations.Max;
import edu.hw10.task1.Annotations.Min;
import java.lang.annotation.Annotation;
import java.util.random.RandomGenerator;
import edu.hw10.task1.UnsupportedObjectException;

public class LongGenerator implements ParameterGenerator<Long> {
    @Override
    public Long generate(RandomGenerator random, Annotation[] annotations) throws UnsupportedObjectException {
        long minValue = Long.MIN_VALUE;
        long maxValue = Long.MAX_VALUE - 1;
        for (var annotation : annotations) {
            if (annotation instanceof Min minAnnotation) {
                minValue = minAnnotation.value();
            } else if (annotation instanceof Max maxAnnotation) {
                maxValue = maxAnnotation.value();
            }
        }
        if (minValue > maxValue) {
            throw new UnsupportedObjectException("lower bound is larger than upper bound");
        }
        return random.nextLong(minValue, maxValue + 1);
    }
}
