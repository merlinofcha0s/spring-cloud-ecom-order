package fr.plb.ecom_order.configuration;

import fr.plb.ecom_order.configuration.resilience4j.SecurityContextPropagator;
import io.github.resilience4j.core.ContextAwareScheduledThreadPoolExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4JConfiguration {

    public final Logger log = LoggerFactory.getLogger(Resilience4JConfiguration.class);

    @Bean
    public ContextAwareScheduledThreadPoolExecutor contextAwareScheduledThreadPool() {
        return ContextAwareScheduledThreadPoolExecutor.newScheduledThreadPool()
                .corePoolSize(10)
                .contextPropagators(new SecurityContextPropagator()).build();
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> executorServiceCustomizer(ContextAwareScheduledThreadPoolExecutor executor) {
        log.info( "Configuring Resilience4JCircuitBreaker executor service with context propagation!" );
        return factory -> {
            // https://docs.spring.io/spring-cloud-circuitbreaker/reference/spring-cloud-circuitbreaker-resilience4j/specific-circuit-breaker-configuration.html
            // https://docs.spring.io/spring-cloud-circuitbreaker/docs/current/reference/html/spring-cloud-circuitbreaker-resilience4j.html#_customizing_the_executorservice
            factory.configureExecutorService(executor);
            factory.configureGroupExecutorService(groupName -> executor);
        };
    }


}
