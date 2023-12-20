package edu.hw11;

import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static org.assertj.core.api.Assertions.assertThat;

public class MethodDelegatorTest {
    @Test
    void methodDelegator_shouldChangeMethodFunctionality()
        throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        ArithmeticUtils utils = MethodDelegator.delegate(ArithmeticUtils.class, DelegatedArithmeticUtils.class, "sum");

        int num1 = 10;
        int num2 = 15;
        int actualOutput = utils.sum(num1, num2);
        int expectedOutput = num1 * num2;

        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    public static class DelegatedArithmeticUtils {
        public static int sum(int a, int b) {
            return a * b;
        }
    }

    public static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }
}
