package edu.hw2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.hw2.Task4.CallingInfo;

public class Task4Test {
    @Test
    void testClassName() {
        CallingInfo callInfo = Task4.callingInfo();

        String expectedClassName = "edu.hw2.Task4Test";
        String actualClassName = callInfo.className();

        assertEquals(expectedClassName, actualClassName);
    }

    @Test
    void testClassAndMethodNames() {
        CallingInfo callInfo = Task4.callingInfo();

        String expectedMethodName = "testClassAndMethodNames";
        String actualMethodName = callInfo.methodName();

        String expectedClassName = "edu.hw2.Task4Test";
        String actualClassName = callInfo.className();

        assertEquals(expectedMethodName, actualMethodName);
        assertEquals(expectedClassName, actualClassName);
    }

    @Test
    void testMethodName1() {
        CallingInfo callInfo = Task4.callingInfo();

        String expectedMethodName = "testMethodName1";
        String actualMethodName = callInfo.methodName();

        assertEquals(expectedMethodName, actualMethodName);
    }

    @Test
    void testMethodName2() {
        CallingInfo callInfo = Task4.callingInfo();

        String expectedMethodName = "testMethodName2";
        String actualMethodName = callInfo.methodName();

        assertEquals(expectedMethodName, actualMethodName);
    }
}
