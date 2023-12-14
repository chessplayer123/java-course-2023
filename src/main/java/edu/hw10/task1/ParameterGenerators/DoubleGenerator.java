package edu.hw10.task1.ParameterGenerators;

import edu.hw10.task1.Annotations.Max;
import edu.hw10.task1.Annotations.Min;
import java.lang.annotation.Annotation;
import java.util.random.RandomGenerator;
import edu.hw10.task1.UnsupportedObjectException;

public class DoubleGenerator implements ParameterGenerator<Double> {
    @Override
    public Double generate(RandomGenerator random, Annotation[] annotations) throws UnsupportedObjectException {
        double minValue = Double.MIN_VALUE;
        double maxValue = Double.MAX_VALUE;
        for (var annotation : annotations) {
            if (annotation instanceof Min minAnnotation) {
                minValue = (double) minAnnotation.value();
            } else if (annotation instanceof Max maxAnnotation) {
                maxValue = (double) maxAnnotation.value();
            }
        }
        if (minValue > maxValue) {
            throw new UnsupportedObjectException("lower bound is larger than upper bound");
        }
        return random.nextDouble(minValue, maxValue);
    }
}
