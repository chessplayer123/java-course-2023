package edu.hw2.Task3;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private static final Logger LOGGER = LogManager.getLogger(FaultyConnection.class);
    private static final double DEFAULT_FAULT_PROBABILITY = 0.1;
    private final double faultProb;

    public FaultyConnection() {
        faultProb = DEFAULT_FAULT_PROBABILITY;
    }

    public FaultyConnection(double faultProb) {
        this.faultProb = faultProb;
    }

    @Override
    public void execute(String command) throws ConnectionException {
        Random random = new Random();
        double randDouble = random.nextDouble();

        if (faultProb >= randDouble) {
            LOGGER.info("Command '{}' failed.", command);
            throw new ConnectionException("Failed to execute command");
        }
        LOGGER.info("Command '{}' was successfully executed.", command);
    }

    @Override
    public void close()  {
        LOGGER.info("Faulty connection was successfully closed.");
    }
}
