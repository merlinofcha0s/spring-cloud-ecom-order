package fr.plb.ecom_order.infrastructure.secondary.service.user;

import fr.plb.ecom_order.configuration.feign.AuthFeignConfiguration;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.JWTTokenDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.LoginDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.RestUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "ecom-user", path = "/api", configuration = AuthFeignConfiguration.class)
public interface AuthServiceFeign {

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    Optional<JWTTokenDTO> authenticate(LoginDTO loginDTO);

    @RequestMapping(value = "/authenticated", method = RequestMethod.GET)
    RestUser getAuthenticatedUser();
}
