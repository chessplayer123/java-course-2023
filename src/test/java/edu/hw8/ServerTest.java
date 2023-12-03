package edu.hw8;

import edu.hw8.QuoteService.Client;
import edu.hw8.QuoteService.QuoteServer;
import edu.hw8.QuoteService.Server;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

public class ServerTest {
    @Test
    void defaultServerShouldReturnExpectedQuoteOnGivenTheme() throws InterruptedException, IOException {
        Server server = new Server(QuoteServer.getDefault(), 1);
        Thread serverThread = new Thread(() -> server.start(9000));
        serverThread.start();

        Thread.sleep(100);

        Client client = Client.connectToServer("localhost", 9000);

        String actualQuote = client.getQuoteByTheme("личности");
        String expectedQuote = "Не переходи на личности там, где их нет";

        client.close();
        serverThread.interrupt();

        assertThat(actualQuote).isEqualTo(expectedQuote);
    }
}
