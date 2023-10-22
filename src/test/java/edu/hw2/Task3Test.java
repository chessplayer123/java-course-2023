package edu.hw2;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import edu.hw2.Task3.*;

public class Task3Test {
    @Test
    void defaultConnectionManagerReturnStableOrFaultyConnection() {
        DefaultConnectionManager manager = new DefaultConnectionManager();
        for (int i = 0; i < 100; ++i) {
            Connection connection = manager.getConnection();

            assertThat(connection).isInstanceOfAny(StableConnection.class, FaultyConnection.class);
        }
    }

    @Test
    void faultConnectionManagerReturnFaultyConnection() {
        FaultyConnectionManager manager = new FaultyConnectionManager();
        for (int i = 0; i < 100; ++i) {
            Connection connection = manager.getConnection();

            assertThat(connection).isInstanceOf(FaultyConnection.class);
        }
    }

    @Test
    void stableConnectionDoesNotThrowException() {
        try(Connection con = new StableConnection()) {
            for (int i = 0; i < 100; ++i) {
                assertThatCode(() -> {
                    con.execute("rm -rf /");
                }).doesNotThrowAnyException();
            }
        } catch (Exception e) {
            // Unreachable
        }
    }

    @Test
    void faultyConnectionThrowAndNotThrowException() {
        boolean isFaultOccured = false;
        boolean isSuccessOccured = false;

        try(Connection con = new FaultyConnection(0.5)) {
            for (int i = 0; i < 100; ++i) {
                try {
                    con.execute("rm -rf /");
                    isSuccessOccured = true;
                } catch (ConnectionException e) {
                    isFaultOccured = true;
                }
            }
        } catch (Exception e) {
            // Unreachable (failed to close connection)
        }

        assertThat(isSuccessOccured).isTrue();
        assertThat(isFaultOccured).isTrue();
    }

    @Test
    void executorThrowsExceptionAfterFailedCommandExecution() {
        double failureProb = 1.0;
        ConnectionManager manager = new FaultyConnectionManager(failureProb);
        int maxAttempts = 3;
        String command = "touch file.txt";

        PopularCommandExecutor executor = new PopularCommandExecutor(maxAttempts, manager);

        assertThatThrownBy(() -> {
            executor.tryExecute(command);
        }).isInstanceOf(ConnectionException.class)
          .hasCauseInstanceOf(ConnectionException.class);
    }

    @Test
    void executorNotThrowsExceptionAfterSuccessfulCommandExecution() {
        double failureConProb = 0.0;
        ConnectionManager manager = new DefaultConnectionManager(failureConProb);
        int maxAttempts = 3;
        String command = "touch file.txt";

        PopularCommandExecutor executor = new PopularCommandExecutor(maxAttempts, manager);

        assertThatCode(() -> {
            executor.tryExecute(command);
        }).doesNotThrowAnyException();
    }
}
