package edu.hw8.QuoteService;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.jetbrains.annotations.NotNull;

public class Client implements Closeable {
    private static final int CONNECTION_TIMEOUT = 1_000;

    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    private Client(Socket socket) throws IOException {
        this.socket = socket;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    public static Client connectToServer(String address, int port) throws IOException {
        Socket socket = new Socket();
        socket.setSoTimeout(CONNECTION_TIMEOUT);
        socket.connect(new InetSocketAddress(address, port));

        return new Client(socket);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }

    private void sendMessage(@NotNull String message) throws IOException {
        output.writeObject(message);
    }

    public String getQuoteByTheme(String theme) throws IOException  {
        sendMessage(theme);
        try {
            return (String) input.readObject();
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }
}
