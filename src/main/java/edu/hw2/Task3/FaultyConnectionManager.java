package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnectionManager implements ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger(FaultyConnectionManager.class);
    private final double probForConToFailExec;
    private static final double DEFAULT_CONNECTION_FAILURE_PROB = 0.1;

    public FaultyConnectionManager() {
        probForConToFailExec = DEFAULT_CONNECTION_FAILURE_PROB;
    }

    public FaultyConnectionManager(double probForConToFailExec) {
        this.probForConToFailExec = probForConToFailExec;
    }

    @Override
    public Connection getConnection() {
        LOGGER.info("Created faulty connection.");
        return new FaultyConnection(probForConToFailExec);
    }
}
