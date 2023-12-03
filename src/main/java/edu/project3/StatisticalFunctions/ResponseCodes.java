package edu.project3.StatisticalFunctions;

import edu.project3.LogRecord;
import edu.project3.Table;
import java.util.HashMap;
import java.util.Map;

public class ResponseCodes implements StatisticalFunction {
    private final Map<Integer, Long> codes;
    private static final Map<Integer, String> RESPONSE_CODES = Map.ofEntries(
        Map.entry(101, "Switching Protocols"),
        Map.entry(200, "OK"),
        Map.entry(201, "Created"),
        Map.entry(202, "Accepted"),
        Map.entry(203, "Non-Authoritative Information"),
        Map.entry(204, "No Content"),
        Map.entry(205, "Reset Content"),
        Map.entry(206, "Partial Content"),
        Map.entry(300, "Multiple Choices"),
        Map.entry(302, "Found"),
        Map.entry(303, "See Other"),
        Map.entry(304, "Not Modified"),
        Map.entry(305, "Use Proxy"),
        Map.entry(400, "Bad Request"),
        Map.entry(401, "Unauthorized"),
        Map.entry(402, "Payment Required"),
        Map.entry(403, "Forbidden"),
        Map.entry(404, "Not Found"),
        Map.entry(405, "Method Not Allowed"),
        Map.entry(406, "Not Acceptable"),
        Map.entry(407, "Proxy Authentication Required"),
        Map.entry(408, "Request Timeout"),
        Map.entry(409, "Conflict"),
        Map.entry(410, "Gone"),
        Map.entry(411, "Length Required"),
        Map.entry(412, "Precondition Failed"),
        Map.entry(413, "Request Entity Too Large"),
        Map.entry(414, "Request-URI Too Long"),
        Map.entry(415, "Unsupported Media Type"),
        Map.entry(416, "Requested Range Not Satisfiable"),
        Map.entry(417, "Expectation Failed"),
        Map.entry(500, "Internal Server Error"),
        Map.entry(501, "Not Implemented"),
        Map.entry(502, "Bad Gateway"),
        Map.entry(503, "Service Unavailable"),
        Map.entry(504, "Gateway Timeout"),
        Map.entry(505, "HTTP Version Not Supported")
    );

    public ResponseCodes() {
        codes = new HashMap<>();
    }

    @Override
    public void process(LogRecord logRecord) {
        codes.merge(logRecord.statusCode(), 1L, Long::sum);
    }

    @Override
    public Table getStatistic() {
        Table table = new Table("Коды ответа", new String[] {"Код", "Имя", "Количество"});

        codes.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> table.addLine(
                String.valueOf(entry.getKey()),
                RESPONSE_CODES.getOrDefault(entry.getKey(), "-"),
                String.valueOf(entry.getValue()))
        );

        return table;
    }
}
