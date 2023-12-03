package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadsIncrementer {
    private final AtomicInteger sharedCounter;
    private final int iterations;

    public ThreadsIncrementer(int iterations) {
        sharedCounter = new AtomicInteger(0);
        this.iterations = iterations;
    }

    private void incrementerThread(int numOfIterations) {
        for (int i = 0; i < numOfIterations; ++i) {
            sharedCounter.getAndIncrement();
        }
    }

    public void startThreads() throws InterruptedException {
        Thread incThread1 = new Thread(() -> incrementerThread(iterations / 2 + iterations % 2));
        Thread incThread2 = new Thread(() -> incrementerThread(iterations / 2));

        incThread1.start();
        incThread2.start();

        incThread1.join();
        incThread2.join();
    }

    public int getCounter() {
        return sharedCounter.get();
    }
}
