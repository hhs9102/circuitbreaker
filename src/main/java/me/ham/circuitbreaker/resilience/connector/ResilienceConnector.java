package me.ham.circuitbreaker.resilience.connector;


import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Component
@Slf4j
public class ResilienceConnector implements Connector {

    AtomicInteger successMethodCallCnt;
    AtomicInteger failureMethodCallCnt;
    AtomicInteger failureFallBackMethodCallCnt;

    public ResilienceConnector() {
        this.successMethodCallCnt = new AtomicInteger(0);
        this.failureMethodCallCnt = new AtomicInteger(0);;
        this.failureFallBackMethodCallCnt = new AtomicInteger(0);;
    }

    @Override
    @CircuitBreaker(name = "hsham", fallbackMethod = "fallback")
    public String success() throws InterruptedException {
        System.out.println(successMethodCallCnt.addAndGet(1) + "This is success.");
        return "This is success.\n ";
    }

    @Override
    @CircuitBreaker(name = "hsham", fallbackMethod = "fallback")
    public String failure() {
        failureMethodCallCnt.addAndGet(1);
        log.info("HystrixService failure method is called");
        throw new RuntimeException("This is runtime Error.");
    }

    public String fallback(Exception e){
        failureFallBackMethodCallCnt.addAndGet(1);
        log.info("This is fallback method.");
        return "This is fallback method.\n";
    }

    //core module 활용
    public String failureLamda(){
        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
        io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("lamda");

        Supplier<String> decoratedSupplier = io.github.resilience4j.circuitbreaker.CircuitBreaker
                .decorateSupplier(circuitBreaker, ()->failure());

        String result = Try.ofSupplier(decoratedSupplier)
                .recover(throwable -> {
                    return "This is fallback method(Lamda)";
                }).get();

        return result;
    }

    @Override
    @CircuitBreaker(name = "hsham", fallbackMethod = "fallback")
    public String circuitBreaker() {
        failureMethodCallCnt.addAndGet(1);
        log.info("ResilienceConnector circuitBreaker method is called");
        throw new RuntimeException("throw runtimeException");
    }

    @Override
    @Bulkhead(name = "hsham", fallbackMethod = "fallback")
    public String bulkhead() throws InterruptedException {
        failureMethodCallCnt.addAndGet(1);
        log.info("ResilienceConnector bulkhead method is called");
        Thread.sleep(1000);
        return "This is bulkhead bulkhead method.\n";
    }

    @Override
    @Retry(name = "hsham", fallbackMethod = "fallback")
    public String retry() {
        failureMethodCallCnt.addAndGet(1);
        log.info("ResilienceConnector retry method is called");
        throw new RuntimeException("throw runtimeException");
    }

    @Override
    @CircuitBreaker(name = "hsham", fallbackMethod = "fallback")
    @Bulkhead(name = "hsham", fallbackMethod = "fallback")
    @Retry(name = "hsham", fallbackMethod = "fallback")
    public String all() {
        failureMethodCallCnt.addAndGet(1);
        log.info("ResilienceConnector all method is called");
        throw new RuntimeException("throw runtimeException");
    }

    public void reset(){
        successMethodCallCnt = new AtomicInteger(0);
        failureMethodCallCnt = new AtomicInteger(0);
        failureFallBackMethodCallCnt =  new AtomicInteger(0);
        print();
    }

    public String print() {
        return "successMethodCallCnt : "+successMethodCallCnt.get() +"\nfailureMethodCallCnt : "+failureMethodCallCnt.get()+"\nfailureFallBackMethodCallCnt : "+failureFallBackMethodCallCnt.get()+"\n";
    }
}
