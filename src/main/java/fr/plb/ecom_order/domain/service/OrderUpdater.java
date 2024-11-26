package fr.plb.ecom_order.domain.service;


import fr.plb.ecom_order.domain.aggregate.*;
import fr.plb.ecom_order.domain.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;

public class OrderUpdater {

  private final OrderRepository orderRepository;

  public OrderUpdater(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public List<OrderedProduct> updateOrderFromStripe(StripeSessionInformation stripeSessionInformation) {
    Order order = orderRepository.findByStripeSessionId(stripeSessionInformation).orElseThrow();
    order.validatePayment();
    orderRepository.updateStatusByPublicId(order.getStatus(), order.getPublicId());
    return order.getOrderedProducts();
  }

  public List<OrderProductQuantity> computeQuantity(List<OrderedProduct> orderedProducts) {
    List<OrderProductQuantity> orderProductQuantities = new ArrayList<>();
    for (OrderedProduct orderedProduct : orderedProducts) {
      OrderProductQuantity orderProductQuantity = OrderProductQuantityBuilder.orderProductQuantity()
        .productPublicId(orderedProduct.productPublicId())
        .quantity(orderedProduct.quantity())
        .build();
      orderProductQuantities.add(orderProductQuantity);
    }
    return orderProductQuantities;
  }
}
