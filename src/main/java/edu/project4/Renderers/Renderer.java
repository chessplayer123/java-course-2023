package edu.project4.Renderers;

import edu.project4.FractalImage;

@FunctionalInterface
public interface Renderer {
    void render(FractalImage image, int samples, RendererParameters params);
}
