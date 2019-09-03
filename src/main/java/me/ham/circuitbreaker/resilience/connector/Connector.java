package me.ham.circuitbreaker.resilience.connector;

public interface Connector {

    String success();

    String failure();

    String failureLamda();
}
