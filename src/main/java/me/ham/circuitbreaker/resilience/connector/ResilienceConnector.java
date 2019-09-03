package me.ham.circuitbreaker.resilience.connector;


import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@CircuitBreaker(name = "hsham")
@Component
public class ResilienceConnector implements Connector {

    @Override
    @CircuitBreaker(name = "hsham", fallbackMethod = "fallback")
    public String success() {
        return "This is success.";
    }

    @Override
    @CircuitBreaker(name = "hsham", fallbackMethod = "fallback")
    public String failure() {
        throw new RuntimeException("This is runtime Error.");
    }

    public String fallback(Exception e){
            return "This is fallback method.";
    }

    public String failureLamda(){
        String result;

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("lamda");

        Supplier<String> decoratedSupplier = io.github.resilience4j.circuitbreaker.CircuitBreaker
                .decorateSupplier(circuitBreaker, ()->failure());

        result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> {
                    return "This is fallback method(Lamda)";
                }).get();

        return result;
    }

}
