package fr.plb.ecom_order.configuration.feign;

import feign.FeignException;
import feign.Logger;
import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import fr.plb.ecom_order.infrastructure.secondary.service.user.AuthServiceFeign;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

public class DefaultClientOpenFeignConfiguration {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DefaultClientOpenFeignConfiguration.class);


    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

//    @Bean
//    public Retryer defaultRetryer() {
//        return new Retryer.Default(300, 500, 3);
//    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return ((methodKey, response) -> {
            int status = response.status();
            if (status == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                log.error("Erreur d'appel {}, {}", methodKey, response.status());
                FeignException feignException = FeignException.errorStatus(methodKey, response);
//                return new RetryableException(
//                        response.status(),
//                        feignException.getMessage(),
//                        response.request().httpMethod(),
//                        feignException,
//                        50L,
//                        response.request());
            }
            return new ErrorDecoder.Default().decode(methodKey, response);
        });
    }

    @Bean
    public RobotAuthenticatorInterceptor robotAuthenticatorInterceptor(AuthServiceFeign authServiceFeign) {
        return new RobotAuthenticatorInterceptor(authServiceFeign);
    }

}
