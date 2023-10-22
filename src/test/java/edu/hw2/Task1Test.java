package edu.hw2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.hw2.Expr.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Task1Test {
    @ParameterizedTest
    @ValueSource(doubles = { 2.71828, -1415.926, 0.0 })
    void testEvaluationOfConstantExpr(double value) {
        Expr constant = new Constant(value);

        double actualValue = constant.evaluate();

        assertEquals(value, actualValue);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 2.71828, -1415.926, 0.0 })
    void testEvaluationOfNegateExpr(double value) {
        Expr constant = new Constant(value);
        Expr negatedConstant = new Negate(constant);

        double actualValue = negatedConstant.evaluate();
        double expectedValue = -value;

        assertEquals(expectedValue, actualValue);
    }

    @ParameterizedTest
    @CsvSource({
        "2.71828, 2.0",
        "1.15, -2.3",
        "4.0, 0.5",
        "0.0, 1000.0",
        "-1.0, 2.0"
    })
    void testEvaluationOfExponentExpr(double base, double exponent) {
        Expr baseConstant = new Constant(base);
        Expr power = new Exponent(baseConstant, exponent);

        double actualValue = power.evaluate();
        double expectedValue = Math.pow(base, exponent);

        assertEquals(expectedValue, actualValue);
    }

    @ParameterizedTest
    @CsvSource({
        "2.71828, 2.0",
        "1.15, -2.3",
        "4.0, 0.5",
        "0.0, 1000.0",
        "-1.0, 2.0"
    })
    void testEvaluationOfMultiplicationExpr(double multiplier1, double multiplier2) {
        Expr constant1 = new Constant(multiplier1);
        Expr constant2 = new Constant(multiplier2);

        Expr multiplication = new Multiplication(constant1, constant2);

        double actualValue = multiplication.evaluate();
        double expectedValue = multiplier1 * multiplier2;

        assertEquals(expectedValue, actualValue);
    }

    @ParameterizedTest
    @CsvSource({
        "2.71828, 2.0",
        "1.15, -2.3",
        "4.0, 0.5",
        "0.0, 1000.0",
        "-1.0, 2.0"
    })
    void testEvaluationOfAdditionExpr(double summand1, double summand2) {
        Expr constant1 = new Constant(summand1);
        Expr constant2 = new Constant(summand2);

        Expr sum = new Addition(constant1, constant2);

        double actualValue = sum.evaluate();
        double expectedValue = summand1 + summand2;

        assertEquals(expectedValue, actualValue);
    }

    @Test
    void testComplexExpression() {
        var one = new Constant(1); // 1
        var two = new Constant(2); // 2
        var four = new Constant(4); // 4

        var negOne = new Negate(one); // -1
        var sumTwoFour = new Addition(two, four); // 2 + 4 = 6
        var mul = new Multiplication(sumTwoFour, negOne); // 6 * (-1) = -6
        var exp = new Exponent(mul, 2); // -6 ^ 2 = 36
        var res = new Addition(exp, one); // 36 + 1

        double expectedValue = 37.0;
        double actualValue = res.evaluate();

        assertEquals(expectedValue, actualValue);
    }
}
