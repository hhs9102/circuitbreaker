package me.ham.circuitbreaker.resilience.connector;

public interface Connector {

    String success() throws InterruptedException;

    String failure();

    String failureLamda();
}
