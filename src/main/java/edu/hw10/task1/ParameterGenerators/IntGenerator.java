package edu.hw10.task1.ParameterGenerators;

import edu.hw10.task1.Annotations.Max;
import edu.hw10.task1.Annotations.Min;
import java.lang.annotation.Annotation;
import java.util.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;

public class IntGenerator implements ParameterGenerator<Integer> {
    @Override
    @NotNull
    public Integer generate(RandomGenerator random, Annotation[] annotations) {
        int minValue = Integer.MIN_VALUE;
        int maxValue = Integer.MAX_VALUE - 1;
        for (var annotation : annotations) {
            if (annotation instanceof Min minAnnotation) {
                minValue = (int) minAnnotation.value();
            } else if (annotation instanceof Max maxAnnotation) {
                maxValue = (int) maxAnnotation.value();
            }
        }
        return random.nextInt(minValue, maxValue + 1);
    }
}
