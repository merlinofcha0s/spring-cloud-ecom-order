package fr.plb.ecom_order.domain.service;


import com.stripe.model.checkout.Session;
import fr.plb.ecom_order.domain.aggregate.DetailCartItemRequest;
import fr.plb.ecom_order.domain.aggregate.Order;
import fr.plb.ecom_order.domain.aggregate.OrderedProduct;
import fr.plb.ecom_order.domain.repository.OrderRepository;
import fr.plb.ecom_order.domain.vo.StripeSessionId;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.stripe.StripeService;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.RestUser;

import java.util.ArrayList;
import java.util.List;

public class OrderCreator {

    private final OrderRepository orderRepository;
    private final StripeService stripeService;

    public OrderCreator(OrderRepository orderRepository, StripeService stripeService) {
        this.orderRepository = orderRepository;
        this.stripeService = stripeService;
    }

    public StripeSessionId create(List<ProductDTO> productsInformations,
                                  List<DetailCartItemRequest> items,
                                  RestUser connectedUser) {

        Session stripeSession = this.stripeService.createPayment(connectedUser,
                productsInformations, items);

        List<OrderedProduct> orderedProducts = new ArrayList<>();

        for (DetailCartItemRequest itemRequest : items) {
            ProductDTO productDetails = productsInformations.stream()
                    .filter(product -> product.publicId().equals(itemRequest.productId().value()))
                    .findFirst().orElseThrow();

            OrderedProduct orderedProduct = OrderedProduct.create(itemRequest.quantity(), productDetails);
            orderedProducts.add(orderedProduct);
        }

        Order order = Order.create(connectedUser.publicId(), orderedProducts,
                new StripeSessionId(stripeSession.getId()), connectedUser.address());
        orderRepository.save(order);

        return new StripeSessionId(stripeSession.getUrl());
    }
}
