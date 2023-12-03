package edu.hw8.QuoteService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ExecutorService connections;
    private final int maxConnectionsNum;
    private final QuoteDatabase quotes;

    public Server(QuoteDatabase quotes, int threadsNum) {
        connections = Executors.newFixedThreadPool(threadsNum);
        maxConnectionsNum = threadsNum;
        this.quotes = quotes;
    }

    public void start(int port) {
        final AtomicInteger currentConnectionsNum = new AtomicInteger(0);

        try (ServerSocket server = new ServerSocket(port)) {
            LOGGER.info("Server listening port {}", port);
            while (true) {
                if (currentConnectionsNum.get() < maxConnectionsNum) {
                    currentConnectionsNum.getAndIncrement();
                    Socket client = server.accept();
                    LOGGER.info("Received new connection from {}", client.getInetAddress());

                    connections.execute(new ServerWorker(client, quotes, currentConnectionsNum));
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            connections.shutdown();
        }
    }
}
