package edu.hw8.QuoteService;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class ServerWorker extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final SocketChannel client;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);
    private final QuoteDatabase quotes;

    public ServerWorker(SocketChannel clientSocket, QuoteDatabase quotes) {
        client = clientSocket;
        this.quotes = quotes;
    }

    private String readMessage() throws IOException {
        StringBuilder message = new StringBuilder();

        buffer.clear();
        while (client.read(buffer) > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            message.append(new String(bytes, StandardCharsets.UTF_8));
        }

        return message.toString();
    }

    private void sendMessage(@NotNull String message) throws IOException {
        client.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void run() {
        while (client.isOpen()) {
            try {
                String theme = readMessage();
                LOGGER.info("Received message '{}' from {}", theme, client.getRemoteAddress());
                sendMessage(quotes.getQuoteByTheme(theme));
            } catch (IOException e) {
                LOGGER.error("Error occurred while reading client's message: {}", e.getMessage());
                break;
            }
        }

        try {
            LOGGER.info("Connection with client {} was closed", client.getRemoteAddress());
            client.close();
        } catch (IOException e) {
            LOGGER.error(
                "Error occurred while trying to close {} connection: {}", client, e.getMessage()
            );
        }
    }
}
