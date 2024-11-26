package fr.plb.ecom_order.infrastructure.secondary.service.user.vo;

import fr.plb.ecom_order.domain.vo.PublicId;
import fr.plb.ecom_order.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public record UserAddressToUpdate(PublicId userPublicId, UserAddress userAddress) {

  public UserAddressToUpdate {
    Assert.notNull("value", userPublicId);
    Assert.notNull("value", userAddress);
  }
}
