package fr.plb.ecom_order.configuration.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import fr.plb.ecom_order.infrastructure.secondary.service.user.AuthServiceFeign;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.JWTTokenDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.LoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class RobotAuthenticatorInterceptor implements RequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer";

    private static final Logger log = LoggerFactory.getLogger(RobotAuthenticatorInterceptor.class);

    public static Optional<JWTTokenDTO> tokenForGateway = Optional.empty();
    private Instant expireTimeInstant;

    private final AuthServiceFeign authServiceFeign;

    @Value("${application.auth.username}")
    private String username;

    @Value("${application.auth.password}")
    private String password;

    @Value("${application.auth.token-validity-in-seconds}")
    private int tokenValidityInSecond;

    public static boolean forceRelogin = false;

    public RobotAuthenticatorInterceptor(AuthServiceFeign authServiceFeign) {
        this.authServiceFeign = authServiceFeign;
    }

    @Override
    public void apply(RequestTemplate template) {
        if (forceRelogin || tokenForGateway.isEmpty()
                || (expireTimeInstant != null && Instant.now().isAfter(expireTimeInstant))) {
            log.info("Login to gateway from the interceptor cause: force?{}, token not present?{}, expire?{}", forceRelogin,
                    tokenForGateway.isEmpty(), (expireTimeInstant != null && Instant.now().isAfter(expireTimeInstant)));
            LoginDTO loginDTO = new LoginDTO(username, password);
            tokenForGateway = authServiceFeign.authenticate(loginDTO);
            expireTimeInstant = Instant.now().plus(tokenValidityInSecond, ChronoUnit.SECONDS);
            forceRelogin = false;
        } else {
            log.info("No need to login again on the gateway: force?{}, token not present?{}, expire?{}", forceRelogin,
                    tokenForGateway.isEmpty(), (expireTimeInstant != null && Instant.now().isAfter(expireTimeInstant)));
        }

        tokenForGateway.ifPresent(jwtToken -> template.header(AUTHORIZATION_HEADER, String.format("%s %s", BEARER,
                jwtToken.idToken())));
    }

    public static void setForceRelogin(boolean forceRelogin) {
        log.info("Force to relogin to the GW");
        RobotAuthenticatorInterceptor.forceRelogin = forceRelogin;
    }
}
