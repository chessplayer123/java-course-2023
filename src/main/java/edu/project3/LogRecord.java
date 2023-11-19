package edu.project3;

import java.time.OffsetDateTime;

public record LogRecord(
    String remoteAddress,
    String remoteUser,
    OffsetDateTime timeLocal,
    String requestType,
    String endpoint,
    String protocol,
    int statusCode,
    int size,
    String httpReferer,
    String httpUserAgent
) {
}
