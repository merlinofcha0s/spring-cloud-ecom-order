package fr.plb.ecom_order.domain.aggregate;

import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductCartDTO;
import org.jilt.Builder;

import java.util.List;

@Builder
public class DetailCartResponse {

  List<ProductCartDTO> products;

  public DetailCartResponse(List<ProductCartDTO> products) {
    this.products = products;
  }

  public List<ProductCartDTO> getProducts() {
    return products;
  }

  public void setProducts(List<ProductCartDTO> products) {
    this.products = products;
  }
}
