package edu.project4.Renderers;

import edu.project4.FractalImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadRenderer implements Renderer {
    private final int numOfThreads;

    public MultiThreadRenderer(int numOfThreads) {
        this.numOfThreads = numOfThreads;
    }

    @Override
    public void render(FractalImage image, int samples, RendererParameters params) {
        final int tasksForThread = Math.ceilDiv(samples, numOfThreads);
        try (ExecutorService pool = Executors.newFixedThreadPool(numOfThreads)) {
            for (int thread = 0; thread < numOfThreads; ++thread) {
                pool.execute(() -> new SingleThreadRenderer().render(image, tasksForThread, params));
            }
        }
    }
}
