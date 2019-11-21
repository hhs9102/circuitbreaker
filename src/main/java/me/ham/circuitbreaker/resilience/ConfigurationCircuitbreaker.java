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
import me.ham.circuitbreaker.resilience.service.ResilienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.time.Duration;

@Configuration
@Slf4j
@DependsOn(value = {"resilienceStateTransitionEventHandler"})
public class ConfigurationCircuitbreaker {

    CircuitBreakerRegistry circuitBreakerRegistry;

    BulkheadRegistry bulkheadRegistry;

    ResilienceService resilienceService;

    RetryRegistry retryRegistry;

    ResilienceStateTransitionEventHandler resilienceStateTransitionEventHandler;

    ResilienceSuccessEventHandler resilienceSuccessEventHandler;


    @Autowired
    public ConfigurationCircuitbreaker(CircuitBreakerRegistry circuitBreakerRegistry, BulkheadRegistry bulkheadRegistry, RetryRegistry retryRegistry, ApplicationContext applicationContext, ResilienceStateTransitionEventHandler resilienceStateTransitionEventHandler, ResilienceSuccessEventHandler resilienceSuccessEventHandler) {
        this.resilienceStateTransitionEventHandler = resilienceStateTransitionEventHandler;
        this.resilienceSuccessEventHandler = resilienceSuccessEventHandler;
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
        circuitBreaker.getEventPublisher().onStateTransition(resilienceStateTransitionEventHandler);
        circuitBreaker.getEventPublisher().onSuccess(resilienceSuccessEventHandler);
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
