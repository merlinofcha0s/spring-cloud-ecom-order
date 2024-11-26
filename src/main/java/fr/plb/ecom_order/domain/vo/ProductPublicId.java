package fr.plb.ecom_order.domain.vo;


import fr.plb.ecom_order.shared.error.domain.Assert;

import java.util.UUID;

public record ProductPublicId(UUID value) {

  public ProductPublicId {
    Assert.notNull("value", value);
  }
}
