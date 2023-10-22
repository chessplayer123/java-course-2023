package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StableConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger(StableConnection.class);

    @Override
    public void execute(String command) {
        LOGGER.info("Command '{}' was successfully executed.", command);
    }

    @Override
    public void close() {
        LOGGER.info("Stable connection was successfully closed.");
    }
}
