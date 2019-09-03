package me.ham.circuitbreaker.resilience.connector;

public interface ResilienceConnector {
    public String sucess();
    public String failure() throws Exception;
}
