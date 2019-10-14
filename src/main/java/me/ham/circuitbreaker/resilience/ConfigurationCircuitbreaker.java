package me.ham.circuitbreaker.resilience;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import me.ham.circuitbreaker.resilience.event.ResilienceStateTransitionEventHandler;
import me.ham.circuitbreaker.resilience.event.ResilienceSuccessEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
@Slf4j
public class ConfigurationCircuitbreaker {

    @Autowired
    CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    BulkheadRegistry bulkheadRegistry;

    @Autowired
    RetryRegistry retryRegistry;

    public ConfigurationCircuitbreaker(CircuitBreakerRegistry circuitBreakerRegistry, BulkheadRegistry bulkheadRegistry, RetryRegistry retryRegistry) {
        circuitBreakerRegist(circuitBreakerRegistry);
        bulkheadRegist(bulkheadRegistry);
        retryRegist(retryRegistry);
    }

    private void circuitBreakerRegist(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreakerConfig circuitBreakerConfig
                = new CircuitBreakerConfig.Builder()
                .ringBufferSizeInClosedState(50)
                .waitDurationInOpenState(Duration.ofMillis(5000))
                .build();
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        circuitBreakerRegistry.circuitBreaker("hsham", circuitBreakerConfig);

        CircuitBreaker circuitBreaker = this.circuitBreakerRegistry.find("hsham").get();
        circuitBreaker.getEventPublisher().onStateTransition(new ResilienceStateTransitionEventHandler());
        circuitBreaker.getEventPublisher().onSuccess(new ResilienceSuccessEventHandler());
    }

    private void bulkheadRegist(BulkheadRegistry bulkheadRegistry) {
        BulkheadConfig bulkheadConfig
                = new BulkheadConfig.Builder()
                                    .maxConcurrentCalls(30)
                                    .maxWaitDuration(Duration.ofMillis(500))
                                    .build();
        this.bulkheadRegistry = bulkheadRegistry;
        this.bulkheadRegistry.bulkhead("hsham",bulkheadConfig);
    }

    private void retryRegist(RetryRegistry retryRegistry) {
        RetryConfig retryConfig = RetryConfig.custom()
                .waitDuration(Duration.ofMillis(100))
                .retryExceptions(RuntimeException.class)
                .maxAttempts(5)
                .build();
        this.retryRegistry = retryRegistry;
        this.retryRegistry.retry("hsham", retryConfig);
    }
}
