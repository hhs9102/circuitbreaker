package me.ham.circuitbreaker.resilience.event;

import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnSuccessEvent;
import io.github.resilience4j.core.EventConsumer;
import org.springframework.stereotype.Component;

@Component
public class ResilienceSuccessEventHandler implements EventConsumer<CircuitBreakerOnSuccessEvent> {
    @Override
    public void consumeEvent(CircuitBreakerOnSuccessEvent event) {
        System.out.println("################CircuitBreakerOnSuccessEvent called################");
        System.out.println(event.toString());
    }
}
