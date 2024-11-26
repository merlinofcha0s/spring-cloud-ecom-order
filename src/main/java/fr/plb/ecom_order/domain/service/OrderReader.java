package fr.plb.ecom_order.domain.service;

import fr.plb.ecom_order.domain.aggregate.Order;
import fr.plb.ecom_order.domain.repository.OrderRepository;
import fr.plb.ecom_order.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class OrderReader {

  private final OrderRepository orderRepository;

  public OrderReader(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  public Page<Order> findAllByUserPublicId(PublicId userPublicId, Pageable pageable) {
    return orderRepository.findAllByUserPublicId(userPublicId, pageable);
  }

  public Page<Order> findAll(Pageable pageable) {
    return orderRepository.findAll(pageable);
  }
}
