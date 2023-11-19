package edu.hw6;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.regex.Pattern;

public class SocketsScannerTest {
    private static final Pattern PORT_INFO_PATTERN = Pattern.compile("(TCP|UDP) +(\\d+) +(.*)");
    private static final String HEADER_FORMAT = "Protocol Port     Service";

    @Test
    void portAvailabilityInfoMatchesTheFormat() {
        String actualOutput = PortsScanner.scan();

        assertThat(actualOutput).startsWith(HEADER_FORMAT);
        assertThat(actualOutput.lines().skip(1)).allMatch(line -> PORT_INFO_PATTERN.matcher(line).matches());
    }
}
