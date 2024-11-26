package fr.plb.ecom_order.infrastructure.secondary.service.user;

import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.JWTTokenDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.LoginDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.RestUser;
import fr.plb.ecom_order.infrastructure.secondary.service.user.vo.UserAddressToUpdate;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<RestUser> getUsersByPublicIds(@RequestParam String publicIds);

    void updateAddress(UserAddressToUpdate userAddressToUpdate);

    RestUser getAuthenticatedUser();

    Optional<JWTTokenDTO> authenticate(LoginDTO loginDTO);
}
