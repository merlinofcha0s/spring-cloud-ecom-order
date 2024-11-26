package fr.plb.ecom_order.infrastructure.secondary.service.user;

import fr.plb.ecom_order.configuration.feign.DefaultClientOpenFeignConfiguration;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.RestUser;
import fr.plb.ecom_order.infrastructure.secondary.service.user.vo.UserAddressToUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ecom-user", path = "/api/users", contextId = "user-resources",
        configuration = DefaultClientOpenFeignConfiguration.class)
public interface UserServiceFeign {

    @RequestMapping(value = "/update-address", method = RequestMethod.POST)
    void updateAddress(UserAddressToUpdate userAddressToUpdate);

    @RequestMapping(value = "/get-by-public-ids", method = RequestMethod.GET)
    List<RestUser> getUsersByPublicIds(@RequestParam String publicIds);
}
