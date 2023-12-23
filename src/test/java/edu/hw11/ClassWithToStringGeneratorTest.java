package edu.hw11;

import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassWithToStringGeneratorTest {
    @Test
    void methodToStringOfGeneratedClass_shouldReturnExpectedString()
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> cls = ClassWithToStringGenerator.generate();

        String actualString = cls.getDeclaredConstructor().newInstance().toString();
        String expectedString = "Hello, ByteBuddy!";

        assertThat(actualString).isEqualTo(expectedString);
    }
}
