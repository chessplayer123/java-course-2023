package edu.hw8.QuoteService;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;

public class Client implements Closeable {
    private final SocketChannel socket;
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    private Client(SocketChannel socket) {
        this.socket = socket;
    }

    public static Client connectToServer(String address, int port) throws IOException {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress(address, port));
        return new Client(socket);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    private void sendMessage(@NotNull String message) throws IOException {
        socket.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
    }

    public String getQuoteByTheme(String theme) throws IOException {
        sendMessage(theme);

        StringBuilder message = new StringBuilder();

        buffer.clear();
        while (socket.read(buffer) > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);

            message.append(new String(bytes, StandardCharsets.UTF_8));
        }

        return message.toString();
    }
}
