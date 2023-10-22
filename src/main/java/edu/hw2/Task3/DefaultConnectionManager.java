package edu.hw2.Task3;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultConnectionManager implements ConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger(DefaultConnectionManager.class);
    private final double faultProb;
    private static final double DEFAULT_FAULT_PROBABILITY = 0.1;

    public DefaultConnectionManager() {
        faultProb = DEFAULT_FAULT_PROBABILITY;
    }

    public DefaultConnectionManager(double faultProb) {
        this.faultProb = faultProb;
    }

    @Override
    public Connection getConnection() {
        Random random = new Random();
        double randDouble = random.nextDouble();

        if (faultProb >= randDouble) {
            LOGGER.info("Created faulty connection.");
            return new FaultyConnection();
        }
        LOGGER.info("Created stable connection.");
        return new StableConnection();
    }
}
