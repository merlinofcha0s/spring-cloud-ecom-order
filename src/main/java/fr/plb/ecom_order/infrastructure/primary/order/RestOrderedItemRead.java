package fr.plb.ecom_order.infrastructure.primary.order;

import fr.plb.ecom_order.domain.aggregate.OrderedProduct;
import org.jilt.Builder;

import java.util.List;

@Builder
public record RestOrderedItemRead(long quantity,
                                  double price,
                                  String name) {

  public static RestOrderedItemRead from(OrderedProduct orderedProduct) {
    return RestOrderedItemReadBuilder.restOrderedItemRead()
      .name(orderedProduct.productName().value())
      .quantity(orderedProduct.quantity().value())
      .price(orderedProduct.price().value())
      .build();
  }

  public static List<RestOrderedItemRead> from(List<OrderedProduct> orderedProducts) {
    return orderedProducts.stream().map(RestOrderedItemRead::from).toList();
  }

}
