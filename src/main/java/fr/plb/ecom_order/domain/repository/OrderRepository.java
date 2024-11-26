package fr.plb.ecom_order.domain.repository;


import fr.plb.ecom_order.domain.aggregate.Order;
import fr.plb.ecom_order.domain.aggregate.StripeSessionInformation;
import fr.plb.ecom_order.domain.vo.OrderStatus;
import fr.plb.ecom_order.domain.vo.PublicId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {

  void save(Order order);

  void updateStatusByPublicId(OrderStatus orderStatus, PublicId orderPublicId);

  Optional<Order> findByStripeSessionId(StripeSessionInformation stripeSessionInformation);

  Page<Order> findAllByUserPublicId(PublicId userPublicId, Pageable pageable);

  Page<Order> findAll(Pageable pageable);

}
