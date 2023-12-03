package edu.hw8.QuoteService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class ServerWorker extends Thread {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Socket client;
    private final ObjectOutputStream output;
    private final ObjectInputStream input;
    private final QuoteDatabase quotes;
    private final AtomicInteger counter;

    public ServerWorker(Socket clientSocket, QuoteDatabase quotes, AtomicInteger counter) throws IOException {
        client = clientSocket;
        output = new ObjectOutputStream(clientSocket.getOutputStream());
        input = new ObjectInputStream(clientSocket.getInputStream());
        this.quotes = quotes;
        this.counter = counter;
    }

    private String readMessage() throws IOException, ClassNotFoundException {
        return (String) input.readObject();
    }

    private void sendMessage(@NotNull String message) throws IOException {
        output.writeObject(message);
    }

    @Override
    public void run() {
        while (client.isConnected()) {
            try {
                String theme = readMessage();
                LOGGER.info("Received message '{}' from {}", theme, client.getInetAddress());
                sendMessage(quotes.getQuoteByTheme(theme));
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.error("Error occurred while reading client's message: {}", e.getMessage());
                break;
            }
        }

        try {
            LOGGER.info("Connection with client {} was closed", client.getInetAddress());
            counter.getAndDecrement();
            client.close();
        } catch (IOException e) {
            LOGGER.error(
                "Error occurred while trying to close {} connection: {}", client, e.getMessage()
            );
        }
    }
}
