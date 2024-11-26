package fr.plb.ecom_order.infrastructure.primary.order;

import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.PictureDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductCartDTO;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestProductCart(String name,
                              double price,
                              String brand,
                              PictureDTO picture,
                              int quantity,
                              UUID publicId) {

  public static RestProductCart from(ProductCartDTO productCart) {
    return RestProductCartBuilder.restProductCart()
      .name(productCart.getName())
      .price(productCart.getPrice())
      .brand(productCart.getBrand())
      .picture(productCart.getPicture())
      .publicId(productCart.getPublicId())
      .build();
  }

  public static List<RestProductCart> from(List<ProductCartDTO> productCarts) {
    return productCarts.stream().map(RestProductCart::from).toList();
  }

}
