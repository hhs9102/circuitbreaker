package me.ham.circuitbreaker.resilience.connector;


import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.vavr.control.Try;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@CircuitBreaker(name = "hsham")
@Component
public class ResilienceAnotherConnector implements Connector {
    AtomicInteger successCnt;
    AtomicInteger failureCnt;
    {
        successCnt = new AtomicInteger(0);
        failureCnt = new AtomicInteger(0);
    }

    @Override
//    @Bulkhead(name = "hsham", fallbackMethod = "fallback")
    @Retry(name = "hsham", fallbackMethod = "fallback")
    public String success() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(successCnt.addAndGet(1) + "This is success.");
        return "This is success.";
    }

    @Override
//    @CircuitBreaker(name = "hsham", fallbackMethod = "fallback")
    @Retry(name = "hsham", fallbackMethod = "fallback")
    public String failure() {
        throw new RuntimeException("This is runtime Error.");
    }

    public String fallback(Exception e){
        System.out.println(failureCnt.addAndGet(1) + "This is fallback method.");
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

    public void reset(){
        successCnt = new AtomicInteger(0);
        failureCnt = new AtomicInteger(0);
        print();
    }

    public String print() {
        String cntStr = "SuccessCnt : "+successCnt+"\nFailureCnt : "+failureCnt;
        System.out.println(cntStr);
        return cntStr;
    }
}
