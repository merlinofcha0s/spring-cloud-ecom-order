package fr.plb.ecom_order.shared.error.infrastructure.primary;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 999)
public class CircuitBreakerErrorsHandler {

    @ExceptionHandler({CallNotPermittedException.class})
    public ProblemDetail handleCallNotPermittedException(CallNotPermittedException e) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.SERVICE_UNAVAILABLE,
                "Service not available due to too many calls"
        );

        problem.setTitle("Too many calls");
        problem.setProperty("circuitbreaker_name", e.getCausingCircuitBreakerName());
        problem.setProperty("message", e.getMessage());
        return problem;
    }

}
