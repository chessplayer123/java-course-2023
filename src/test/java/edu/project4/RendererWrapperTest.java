package edu.project4;

import edu.project4.Renderers.RendererWrapper;
import edu.project4.Renderers.SingleThreadRenderer;
import org.junit.jupiter.api.Test;

public class RendererWrapperTest {
    @Test
    void wrapperProduceExpectedParameters() {
        RendererWrapper.wrap(new SingleThreadRenderer())
            .buildParameters(0);
    }
}
