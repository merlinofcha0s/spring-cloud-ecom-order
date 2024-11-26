package fr.plb.ecom_order.infrastructure.secondary.service.product.vo;


import fr.plb.ecom_order.shared.error.domain.Assert;

public record ProductName(String value) {

  public ProductName {
    Assert.notNull("value", value);
    Assert.field("value", value).minLength(3).maxLength(256);
  }
}
