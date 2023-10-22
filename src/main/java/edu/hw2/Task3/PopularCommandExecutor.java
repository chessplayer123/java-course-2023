package edu.hw2.Task3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;
    static private final Logger LOGGER = LogManager.getLogger(PopularCommandExecutor.class);

    public PopularCommandExecutor(int maxAttempts, ConnectionManager manager) {
        this.maxAttempts = maxAttempts;
        this.manager = manager;
    }

    public void tryExecute(String command) throws ConnectionException {
        try (Connection connection = manager.getConnection()) {
            Throwable cause = null;
            for (int attempt = 0; attempt < maxAttempts; ++attempt) {
                try {
                    connection.execute(command);
                    return;
                } catch (ConnectionException exception) {
                    cause = exception.getCause();
                    LOGGER.info("Retrying ...");
                }
            }
            throw new ConnectionException("Failed to execute command", cause);
        } catch (Exception e) {
            throw new ConnectionException("Failed to close connection", e);
        }
    }
}
