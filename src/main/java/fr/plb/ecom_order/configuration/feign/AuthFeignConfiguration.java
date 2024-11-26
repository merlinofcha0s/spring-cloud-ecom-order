package fr.plb.ecom_order.configuration.feign;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;

public class AuthFeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                Optional<Jwt> principal = Optional.ofNullable((Jwt) SecurityContextHolder
                        .getContext().getAuthentication().getPrincipal());
                principal.ifPresent(jwt -> requestTemplate.header("Authorization",
                        "Bearer " + principal.get().getTokenValue()));
            }
        };
    }
}
