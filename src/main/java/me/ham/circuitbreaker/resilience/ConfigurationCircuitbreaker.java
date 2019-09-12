package me.ham.circuitbreaker.resilience;

import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Objects;

@Configuration
public class ConfigurationCircuitbreaker {

    @Autowired
    private final BulkheadRegistry bulkheadRegistry;

    @Autowired
    private final RetryRegistry retryRegistry;

    public ConfigurationCircuitbreaker(BulkheadRegistry bulkheadRegistry, RetryRegistry retryRegistry) {
        BulkheadConfig bulkheadConfig = new BulkheadConfig.Builder()
                                                            .maxConcurrentCalls(30)
                                                            .maxWaitDuration(Duration.ofMillis(500))
                                                            .build();
        this.bulkheadRegistry = bulkheadRegistry;
        bulkheadRegistry.bulkhead("hsham",bulkheadConfig);

        RetryConfig retryConfig = RetryConfig.custom()
                .waitDuration(Duration.ofMillis(100))
                .retryExceptions(RuntimeException.class)
                .maxAttempts(5)
                .build();

        this.retryRegistry = retryRegistry;
        retryRegistry.retry("hsham", retryConfig);

        retryRegistry.getEventPublisher().onEntryAdded(
                entryAddedEvent -> {
                    Retry addedRetry = entryAddedEvent.getAddedEntry();
                    System.out.println("############"+addedRetry.getName() +"is added");
                }
        );
    }
}
