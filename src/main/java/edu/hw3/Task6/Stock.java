package edu.hw3.Task6;

import org.jetbrains.annotations.NotNull;

public record Stock(double value) implements Comparable<Stock> {
    @Override
    public int compareTo(@NotNull Stock other) {
        return Double.compare(value, other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != Stock.class) {
            return false;
        }
        return ((Stock) o).value == value;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(value);
    }
}
