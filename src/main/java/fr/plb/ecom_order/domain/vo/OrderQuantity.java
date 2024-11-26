package fr.plb.ecom_order.domain.vo;

import fr.plb.ecom_order.shared.error.domain.Assert;

public record OrderQuantity(long value) {

  public OrderQuantity {
    Assert.field("value", value).positive();

  }
}
