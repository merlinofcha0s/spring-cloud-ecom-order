package fr.plb.ecom_order.configuration.feign;

import feign.Logger;
import fr.plb.ecom_order.infrastructure.secondary.service.user.AuthServiceFeign;
import org.springframework.context.annotation.Bean;

public class DefaultClientOpenFeignConfiguration {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public RobotAuthenticatorInterceptor robotAuthenticatorInterceptor(AuthServiceFeign authServiceFeign) {
        return new RobotAuthenticatorInterceptor(authServiceFeign);
    }

}
