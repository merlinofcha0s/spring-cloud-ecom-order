package fr.plb.ecom_order.domain.vo;


import fr.plb.ecom_order.shared.error.domain.Assert;

public record OrderPrice(double value) {

  public OrderPrice {
    Assert.field("value", value).strictlyPositive();
  }
}
