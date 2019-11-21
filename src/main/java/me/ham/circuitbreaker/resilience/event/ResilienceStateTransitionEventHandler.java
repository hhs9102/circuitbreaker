package me.ham.circuitbreaker.resilience.event;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import io.github.resilience4j.core.EventConsumer;
import me.ham.circuitbreaker.entity.Status;
import me.ham.circuitbreaker.repository.ResilienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResilienceStateTransitionEventHandler implements EventConsumer<CircuitBreakerOnStateTransitionEvent> {

    @Autowired
    private ResilienceRepository resilienceRepository;


    @Override
    public void consumeEvent(CircuitBreakerOnStateTransitionEvent event) {
        System.out.println("consumeEvent Thread name : "+Thread.currentThread().getName());
        CircuitBreaker.StateTransition stateTransition = event.getStateTransition();
        System.out.println("################consumeEvent called################");
        System.out.println(event.toString());

        String toState = event.getStateTransition().getToState().name();

        Status status = resilienceRepository.findById(1l).get();
        status.setState(toState);
        resilienceRepository.save(status);

        if(CircuitBreaker.StateTransition.CLOSED_TO_OPEN == stateTransition) {
            System.out.println("CLOSED_TO_OPEN");
        }else if(CircuitBreaker.StateTransition.OPEN_TO_HALF_OPEN == stateTransition){
            System.out.println("OPEN_TO_HALF_OPEN");
        }else if(CircuitBreaker.StateTransition.OPEN_TO_CLOSED == stateTransition){
            System.out.println("OPEN_TO_CLOSED");
        }else if(CircuitBreaker.StateTransition.HALF_OPEN_TO_CLOSED == stateTransition){
            System.out.println("HALF_OPEN_TO_CLOSED");
        }
    }
}
