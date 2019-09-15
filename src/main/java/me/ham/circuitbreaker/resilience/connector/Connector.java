package me.ham.circuitbreaker.resilience.connector;

public interface Connector {

    String success() throws InterruptedException;

    String failure();

    String failureLamda();

    String circuitBreaker();

    String bulkhead() throws InterruptedException;

    String retry();

    String all();
}
