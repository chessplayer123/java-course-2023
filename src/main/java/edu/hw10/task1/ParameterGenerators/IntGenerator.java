package edu.hw10.task1.ParameterGenerators;

import edu.hw10.task1.Annotations.Max;
import edu.hw10.task1.Annotations.Min;
import java.lang.annotation.Annotation;
import java.util.random.RandomGenerator;
import edu.hw10.task1.UnsupportedObjectException;

public class IntGenerator implements ParameterGenerator<Integer> {
    @Override
    public Integer generate(RandomGenerator random, Annotation[] annotations) throws UnsupportedObjectException {
        int minValue = Integer.MIN_VALUE;
        int maxValue = Integer.MAX_VALUE - 1;
        for (var annotation : annotations) {
            if (annotation instanceof Min minAnnotation) {
                minValue = (int) minAnnotation.value();
            } else if (annotation instanceof Max maxAnnotation) {
                maxValue = (int) maxAnnotation.value();
            }
        }
        if (minValue > maxValue) {
            throw new UnsupportedObjectException("lower bound is larger than upper bound");
        }
        return random.nextInt(minValue, maxValue + 1);
    }
}
