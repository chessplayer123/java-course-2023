package edu.hw8.QuoteService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger();

    private final AtomicInteger currentConnectionsNum;
    private final ExecutorService connections;
    private final int maxConnectionsNum;
    private final QuoteDatabase quotes;

    public Server(QuoteDatabase quotes, int threadsNum) {
        connections = Executors.newFixedThreadPool(threadsNum);
        currentConnectionsNum = new AtomicInteger(0);
        maxConnectionsNum = threadsNum;
        this.quotes = quotes;
    }

    private void handleConnections(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        LOGGER.info("Server was successfully started: {}", serverChannel.getLocalAddress());
        while (serverChannel.isOpen()) {
            if (selector.select() <= 0) {
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable() && currentConnectionsNum.get() < maxConnectionsNum) {
                    currentConnectionsNum.getAndIncrement();

                    SocketChannel client = serverChannel.accept();
                    LOGGER.info("Received new connection from {}", client.getRemoteAddress());

                    connections.execute(new ServerWorker(client, quotes));
                    iterator.remove();
                }
            }
        }
    }

    public void start(int port) {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            Selector selector = Selector.open();

            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverChannel.bind(new InetSocketAddress(port));

            handleConnections(serverChannel, selector);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
            connections.shutdown();
        }
    }
}
