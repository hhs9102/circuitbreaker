package me.ham.circuitbreaker.resilience.event;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.EventConsumer;
import org.springframework.stereotype.Component;

@Component
public class ResilienceStateTransitionEventHandler implements EventConsumer<CircuitBreakerOnStateTransitionEvent> {

    @Override
    public void consumeEvent(CircuitBreakerOnStateTransitionEvent event) {
        CircuitBreaker.StateTransition stateTransition = event.getStateTransition();
        System.out.println("################consumeEvent called################");
        System.out.println(event.toString());
        if(CircuitBreaker.StateTransition.CLOSED_TO_OPEN == stateTransition){
            System.out.println("CLOSED_TO_OPEN");
        }else if(CircuitBreaker.StateTransition.OPEN_TO_HALF_OPEN == stateTransition){
            System.out.println("OPEN_TO_HALF_OPEN");
        }else if(CircuitBreaker.StateTransition.OPEN_TO_CLOSED == stateTransition){
            System.out.println("OPEN_TO_CLOSED");
        }
    }
}
