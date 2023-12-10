package edu.hw9;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class PredicateFileFinder extends RecursiveTask<Stream<File>> {
    private final Path root;
    private final Predicate<File> predicate;

    public PredicateFileFinder(Path root, Predicate<File> predicate) throws IllegalArgumentException {
        if (!root.toFile().isDirectory()) {
            throw new IllegalArgumentException("root must be directory");
        }
        this.root = root;
        this.predicate = predicate;
    }

    @Override
    protected Stream<File> compute() {
        Stream.Builder<File> builder = Stream.builder();
        List<ForkJoinTask<Stream<File>>> subtasks = new ArrayList<>();

        for (File file : root.toFile().listFiles()) {
            if (predicate.test(file)) {
                builder.add(file);
            }
            if (file.isDirectory()) {
                subtasks.add(new PredicateFileFinder(file.toPath(), predicate).fork());
            }
        }

        Stream<File> result = builder.build();
        for (var subtask : subtasks) {
            result = Stream.concat(result, subtask.join());
        }
        return result;
    }
}
