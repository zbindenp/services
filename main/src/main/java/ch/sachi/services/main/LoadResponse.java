package ch.sachi.services.main;

public record LoadResponse(String hostname, long durationNanos, String headers) {
    long durationMillis() {
        return durationNanos / 1000000;
    }
}