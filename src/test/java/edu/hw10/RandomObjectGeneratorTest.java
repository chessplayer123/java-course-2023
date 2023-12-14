package edu.hw10;

import edu.hw10.task1.Annotations.Max;
import edu.hw10.task1.Annotations.Min;
import edu.hw10.task1.Annotations.NotNull;
import edu.hw10.task1.ParameterGenerators.ParameterGenerator;
import edu.hw10.task1.RandomObjectGenerator;
import edu.hw10.task1.UnsupportedObjectException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Random;

public class RandomObjectGeneratorTest {
    @RepeatedTest(16)
    void generateObjectFromFactoryMethod() throws UnsupportedObjectException {
        Random random = new Random();
        RandomObjectGenerator generator = new RandomObjectGenerator(random, ParameterGenerator.getDefault());

        var actualObject = generator.nextObject(ClassWithFactoryMethod.class, "create");

        assertThat(actualObject).isNotNull();
    }

    @RepeatedTest(32)
    void generateObjectWithMinAndMaxAnnotations_shouldFollowAnnotation() throws UnsupportedObjectException {
        Random random = new Random();
        RandomObjectGenerator generator = new RandomObjectGenerator(random);

        RecordWithMinMax actualRecord = generator.nextObject(RecordWithMinMax.class);

        assertThat(actualRecord.intValue()).isBetween(10, 12);
        assertThat(actualRecord.doubleValue()).isBetween(0.0, 1.0);
        assertThat(actualRecord.stringValue()).hasSizeBetween(0, 16);
        assertThat(actualRecord.longValue()).isBetween(1_000_000L, 1_000_000_000L);
    }

    @RepeatedTest(16)
    void unsupportedParameter_shouldBeEqualToNull() throws UnsupportedObjectException {
        Random random = new Random();
        RandomObjectGenerator generator = new RandomObjectGenerator(random);

        var actualRecord = generator.nextObject(RecordWithUnsupportedParameter.class);
        var expectedRecord = new RecordWithUnsupportedParameter(null);

        assertThat(actualRecord).isEqualTo(expectedRecord);
    }

    @Test
    void unsupportedParameterWithNotNullAnnotation_shouldThrowException() {
        Random random = new Random();
        RandomObjectGenerator generator = new RandomObjectGenerator(random);

        assertThatThrownBy(() -> generator.nextObject(RecordWithNotNullUnsupportedParameter.class))
            .isInstanceOf(UnsupportedObjectException.class);
    }

    public record RecordWithMinMax(
        @Min(10) @Max(12) Integer intValue,
        @Min(0) @Max(1) double doubleValue,
        @Min(1_000_000) @Max(1_000_000_000) Long longValue,
        @Min(0) @Max(16) String stringValue
    ) {
    }

    public record RecordWithUnsupportedParameter(List<Integer> list) {
    }

    public record RecordWithNotNullUnsupportedParameter(@NotNull List<Integer> list) {
    }

    public static class ClassWithFactoryMethod {
        private String name;
        private double precision;
        boolean flag;

        private ClassWithFactoryMethod(String name, double precision, boolean flag) {
            this.name = name;
            this.precision = precision;
            this.flag = flag;
        }

        public static ClassWithFactoryMethod create(String name, double precision, boolean flag) {
            return new ClassWithFactoryMethod(name, precision, flag);
        }
    }
}
