package edu.project3;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.IntStream;

public class Table {
    private final int cols;
    private final String title;
    private final List<String[]> data;

    public Table(@NotNull String title, String[] header) {
        this.title = title;
        cols = header.length;
        data = new ArrayList<>();

        data.add(header);
    }

    public String getTitle() {
        return title;
    }

    public String[] get(int i) {
        return data.get(i);
    }

    public int getCols() {
        return cols;
    }

    public int getRows() {
        return data.size();
    }

    public void addLine(String... line) throws IllegalArgumentException {
        if (line.length != cols) {
            throw new IllegalArgumentException("Lines must have the same size");
        }
        data.add(line);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o.getClass() != Table.class) {
            return false;
        }
        Table other = (Table) o;
        return data.size() == other.data.size()
            && IntStream.range(0, data.size()).allMatch(i -> Arrays.equals(get(i), other.get(i)));
    }
}
