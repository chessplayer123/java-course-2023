package edu.hw8.ThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final BlockingQueue<Runnable> tasks;
    private boolean isStarted;

    private FixedThreadPool(int numOfThreads) {
        threads = new Thread[numOfThreads];
        tasks = new LinkedBlockingQueue<>();
        isStarted = false;
    }

    public static FixedThreadPool create(int numOfThreads) {
        return new FixedThreadPool(numOfThreads);
    }

    @Override
    public void awaitTermination() throws InterruptedException {
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Override
    public void start() {
        if (isStarted) {
            return;
        }

        isStarted = true;
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(() -> {
                while (!tasks.isEmpty()) {
                    try {
                        tasks.take().run();
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
            threads[i].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (!isStarted) {
            tasks.add(runnable);
        }
    }

    @Override
    public void close() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
        isStarted = false;
    }
}
