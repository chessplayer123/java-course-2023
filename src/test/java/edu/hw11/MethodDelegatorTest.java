package edu.hw11;

import net.bytebuddy.agent.ByteBuddyAgent;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MethodDelegatorTest {
    @Test
    void methodDelegator_shouldChangeMethodFunctionality() {
        ByteBuddyAgent.install();
        MethodDelegator.delegate(ArithmeticUtils.class, DelegatedArithmeticUtils.class, "sum");

        ArithmeticUtils utils = new ArithmeticUtils();

        int num1 = 10;
        int num2 = 15;
        int actualOutput = utils.sum(num1, num2);
        int expectedOutput = num1 * num2;

        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    public class DelegatedArithmeticUtils {
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
