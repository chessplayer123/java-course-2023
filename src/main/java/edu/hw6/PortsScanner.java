package edu.hw6;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Map;

public final class PortsScanner {
    private static final Map<Integer, String> KNOWN_PORTS = Map.ofEntries(
        Map.entry(1,     "TCP Port Service Multiplexer"),
        Map.entry(22,    "Secure Shell"),
        Map.entry(23,    "Telnet"),
        Map.entry(24,    "Simple Mail Transfer Protocol"),
        Map.entry(42,    "Host Name Server Protocol"),
        Map.entry(67,    "Bootstrap Protocol Server"),
        Map.entry(68,    "Bootstrap Protocol Client"),
        Map.entry(69,    "Trivial File Transfer Protocol"),
        Map.entry(70,    "Gopher protocol"),
        Map.entry(79,    "Finger protocol"),
        Map.entry(135,   "EPMAP"),
        Map.entry(138,   "NetBIOS Datagram Service"),
        Map.entry(139,   "NetBIOS Session Service"),
        Map.entry(143,   "Internet Message Access Protocol"),
        Map.entry(209,   "Quick Mail Transfer Protocol"),
        Map.entry(674,   "Application Configuration Access Protocol"),
        Map.entry(843,   "Adobe Flash"),
        Map.entry(847,   "DHCP Failover protocol"),
        Map.entry(873,   "rsync file synchronization protocol"),
        Map.entry(1234,  "VLC media player"),
        Map.entry(1521,  "Oracle database"),
        Map.entry(1723,  "Point-to-Point Tunneling Protocol"),
        Map.entry(1900,  "Simple Service Discovery Protocol"),
        Map.entry(5037,  "Android ADB server"),
        Map.entry(5222,  "Extensible Messaging and Presence Protocol"),
        Map.entry(5353,  "Multicast DNS"),
        Map.entry(5355,  "Link-Local Multicast Name Resolution"),
        Map.entry(5432,  "PostgreSQL database system"),
        Map.entry(6379,  "Redis key-value data store"),
        Map.entry(27017, "MongoDB daemon process")
    );
    private static final int UPPER_PORTS_BOUND = 49151;
    private static final String LINE_FORMAT = "%-8s %-8s %s\n";
    private static final String UNKNOWN_SERVICE = "N/A";

    private PortsScanner() {
    }

    public static boolean isBusyTCPPort(int port) {
        try (ServerSocket ignored = new ServerSocket(port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    public static boolean isBusyUDPPort(int port) {
        try (DatagramSocket ignored = new DatagramSocket(port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    public static String scan() {
        StringBuilder report = new StringBuilder();

        report.append(LINE_FORMAT.formatted("Protocol", "Port", "Service"));
        for (int port = 0; port <= UPPER_PORTS_BOUND; ++port) {
            if (isBusyTCPPort(port)) {
                report.append(LINE_FORMAT.formatted("TCP", port, KNOWN_PORTS.getOrDefault(port, UNKNOWN_SERVICE)));
            } else if (isBusyUDPPort(port)) {
                report.append(LINE_FORMAT.formatted("UDP", port, KNOWN_PORTS.getOrDefault(port, UNKNOWN_SERVICE)));
            }
        }

        return report.toString();
    }
}
