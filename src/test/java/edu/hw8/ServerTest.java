package edu.hw8;

import edu.hw8.QuoteService.Client;
import edu.hw8.QuoteService.QuoteServer;
import edu.hw8.QuoteService.Server;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.SocketTimeoutException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ServerTest {
    @Test
    void defaultServerShouldReturnExpectedQuoteOnGivenTheme(
    ) throws InterruptedException, IOException  {
        Server server = new Server(QuoteServer.getDefault(), 1);
        Thread serverThread = new Thread(() -> server.start(9000));
        serverThread.start();

        // switch context, let server start
        Thread.sleep(100);

        Client client = Client.connectToServer("localhost", 9000);

        String actualQuote = client.getQuoteByTheme("личности");
        String expectedQuote = "Не переходи на личности там, где их нет";

        client.close();
        serverThread.interrupt();

        assertThat(actualQuote).isEqualTo(expectedQuote);
    }

    @Test
    void clientForWhichThereAreNoThreadsIsWaiting() throws InterruptedException, IOException {
        Server server = new Server(QuoteServer.getDefault(), 1);
        Thread serverThread = new Thread(() -> server.start(9001));
        serverThread.start();

        // switch context, let server start
        Thread.sleep(100);

        Client prevClient = Client.connectToServer("localhost", 9001);
        assertThatThrownBy(() -> Client.connectToServer("localhost", 9001))
            .isInstanceOf(SocketTimeoutException.class)
            .hasMessage("Read timed out");
    }

    @Test
    void closedClientFreesUpThreadForOther() throws InterruptedException, IOException {
        Server server = new Server(QuoteServer.getDefault(), 1);
        Thread serverThread = new Thread(() -> server.start(9002));
        serverThread.start();

        // switch context, let server start
        Thread.sleep(100);

        Client prevClient = Client.connectToServer("localhost", 9002);
        prevClient.close();

        Client newClient = Client.connectToServer("localhost", 9002);
        String actualQuote = newClient.getQuoteByTheme("интеллект");
        String expectedQuote = "Чем ниже интеллект, тем громче оскорбления";
    }

    @Test
    void multipleClientsWorksInParallel() throws InterruptedException, IOException {
        Server server = new Server(QuoteServer.getDefault(), 2);
        Thread serverThread = new Thread(() -> server.start(9003));
        serverThread.start();

        // switch context, let server start
        Thread.sleep(100);

        Client client1 = Client.connectToServer("localhost", 9003);
        Client client2 = Client.connectToServer("localhost", 9003);

        String actualQuote = "%s\n%s".formatted(
            client1.getQuoteByTheme("интеллект"),
            client2.getQuoteByTheme("личности")
        );

        String expectedQuote = "Чем ниже интеллект, тем громче оскорбления\nНе переходи на личности там, где их нет";

        assertThat(actualQuote).isEqualTo(expectedQuote);
    }
}
