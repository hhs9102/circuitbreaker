package me.ham.circuitbreaker.resilience;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ConfigurationCircuitbreaker {

    @Autowired
    private final BulkheadRegistry bulkheadRegistry;

    public ConfigurationCircuitbreaker(BulkheadRegistry bulkheadRegistry) {
        BulkheadConfig bulkheadConfig = new BulkheadConfig.Builder()
                                                            .maxConcurrentCalls(30)
                                                            .maxWaitDuration(Duration.ofMillis(500))
                                                            .build();
        this.bulkheadRegistry = bulkheadRegistry;
        bulkheadRegistry.bulkhead("hsham",bulkheadConfig);
    }
}
