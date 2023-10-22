package edu.hw2;

public sealed interface Expr {
    double evaluate();

    public record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }
    }

    public record Negate(Expr arg) implements Expr {
        @Override
        public double evaluate() {
            return -arg.evaluate();
        }
    }

    public record Exponent(Expr base, double power) implements Expr {
        @Override
        public double evaluate() {
            return Math.pow(base.evaluate(), power);
        }
    }

    public record Addition(Expr arg1, Expr arg2) implements Expr {
        @Override
        public double evaluate() {
            return arg1.evaluate() + arg2.evaluate();
        }
    }

    public record Multiplication(Expr arg1, Expr arg2) implements Expr {
        @Override
        public double evaluate() {
            return arg1.evaluate() * arg2.evaluate();
        }
    }
}
