package me.ham.circuitbreaker.resilience.connector;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;

@Component
@CircuitBreaker(name = "hsham")
public class ResilienceConnectorImpl implements ResilienceConnector{
    @Override
    public String sucess() {
        return "This is success method";
    }

    @Override
    @CircuitBreaker(name = "hsham", fallbackMethod = "failureFallback")
    public String failure() throws Exception {
        throw new Exception("This is failure");
    }

    public String failureFallback(){
        return "This is failureFallBack Method.";
    }
}
