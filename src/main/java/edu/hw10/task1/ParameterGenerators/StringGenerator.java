package edu.hw10.task1.ParameterGenerators;

import edu.hw10.task1.Annotations.Max;
import edu.hw10.task1.Annotations.Min;
import java.lang.annotation.Annotation;
import java.util.random.RandomGenerator;
import org.jetbrains.annotations.NotNull;

public class StringGenerator implements ParameterGenerator<String> {
    private static final int LENGTH_LOWER_BOUND = 0;
    private static final int LENGTH_UPPER_BOUND = 100;

    private final int charsLowerBound;
    private final int charsUpperBound;

    public StringGenerator(int charsLowerBound, int charsUpperBound) {
        this.charsLowerBound = charsLowerBound;
        this.charsUpperBound = charsUpperBound;
    }

    @Override
    @NotNull
    public String generate(RandomGenerator random, Annotation[] annotations) {
        int minLen = LENGTH_LOWER_BOUND;
        int maxLen = LENGTH_UPPER_BOUND;
        for (var annotation : annotations) {
            if (annotation instanceof Min minAnnotation) {
                minLen = (int) minAnnotation.value();
            } else if (annotation instanceof Max maxAnnotation) {
                maxLen = (int) maxAnnotation.value();
            }
        }
        int length = random.nextInt(minLen, maxLen + 1);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            builder.append((char) random.nextInt(charsLowerBound, charsUpperBound + 1));
        }
        return builder.toString();
    }
}
