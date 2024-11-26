package fr.plb.ecom_order.configuration;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = {"fr.plb.ecom_order.infrastructure.secondary.service.*"})
public class OpenFeignConfiguration {
}
