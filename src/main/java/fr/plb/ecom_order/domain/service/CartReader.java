package fr.plb.ecom_order.domain.service;

import fr.plb.ecom_order.domain.aggregate.DetailCartResponse;
import fr.plb.ecom_order.domain.aggregate.DetailCartResponseBuilder;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductCartDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductDTO;

import java.util.List;

public class CartReader {

  public CartReader() {
  }

  public DetailCartResponse getDetails(List<ProductDTO> products) {
    List<ProductCartDTO> cartProducts = products.stream().map(ProductCartDTO::from).toList();
    return DetailCartResponseBuilder.detailCartResponse().products(cartProducts)
      .build();
  }
}
