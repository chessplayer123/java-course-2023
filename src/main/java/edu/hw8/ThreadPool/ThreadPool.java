package edu.hw8.ThreadPool;

public interface ThreadPool extends AutoCloseable {
    void start();

    void execute(Runnable runnable);

    void awaitTermination() throws InterruptedException;
}
