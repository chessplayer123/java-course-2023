package edu.hw9;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.List;

public class StatsCollectorTest {
    @RepeatedTest(64)
    void pushMetricActuallyAddMetric() throws InterruptedException {
        StatsCollector collector = new StatsCollector();

        collector.push("metric1", new double[] {1, 2, 3, 4});
        collector.push("metric2", new double[] {1.5, 2.5, -4.0, 16.0});

        List<StatsCollector.Metric> expectedMetrics = List.of(
            new StatsCollector.Metric("metric1", 10.0, 1.0, 4.0, 2.5),
            new StatsCollector.Metric("metric2", 16.0, -4.0, 16.0, 4.0)
        );
        List<StatsCollector.Metric> actualMetrics = collector.stats();

        assertThat(actualMetrics).containsExactlyInAnyOrderElementsOf(expectedMetrics);
    }

    @Test
    void metricFromMethod_shouldProduceExpectedStatistics() {
        double[] data = new double[] {10.0, -1.3, -1024.8, 0.0, 9.3, 8.4};

        String name = "metric-name";
        double sum = data[0] + data[1] + data[2] + data[3] + data[4] + data[5];
        double max = data[0];
        double min = data[2];
        double avg = sum / 6.0;

        StatsCollector.Metric actualMetric = StatsCollector.Metric.from(name, data);

        Assertions.assertAll(
            () -> assertThat(actualMetric.name()).isEqualTo(name),
            () -> assertThat(actualMetric.sum()).isCloseTo(sum, Percentage.withPercentage(0.00001)),
            () -> assertThat(actualMetric.max()).isEqualTo(max),
            () -> assertThat(actualMetric.min()).isEqualTo(min),
            () -> assertThat(actualMetric.avg()).isCloseTo(avg, Percentage.withPercentage(0.00001))
        );
    }

    @Test
    void addingEmptyMetricThrowsException() {
        StatsCollector collector = new StatsCollector();

        assertThatThrownBy(() -> collector.push("metric", new double[] {}))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("metric can't be empty");
    }

    @Test
    void createMetricFromEmptyArrayThrowsException() {
        assertThatThrownBy(() -> StatsCollector.Metric.from("metric", new double[] {}))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("metric can't be empty");
    }
}
