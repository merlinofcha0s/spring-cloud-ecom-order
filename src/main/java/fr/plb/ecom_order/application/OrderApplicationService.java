package fr.plb.ecom_order.application;


import fr.plb.ecom_order.domain.aggregate.*;
import fr.plb.ecom_order.domain.repository.OrderRepository;
import fr.plb.ecom_order.domain.service.CartReader;
import fr.plb.ecom_order.domain.service.OrderCreator;
import fr.plb.ecom_order.domain.service.OrderReader;
import fr.plb.ecom_order.domain.service.OrderUpdater;
import fr.plb.ecom_order.domain.vo.PublicId;
import fr.plb.ecom_order.domain.vo.StripeSessionId;
import fr.plb.ecom_order.infrastructure.secondary.service.product.ProductServiceFeign;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.stripe.StripeService;
import fr.plb.ecom_order.infrastructure.secondary.service.user.AuthServiceFeign;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.RestUser;
import fr.plb.ecom_order.infrastructure.secondary.service.user.vo.UserAddress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderApplicationService {

    private final ProductServiceFeign productService;
    private final CartReader cartReader;
    private final AuthServiceFeign authServiceFeign;
    private final UserServiceFeign userServiceFeign;
    private final OrderCreator orderCreator;
    private final OrderUpdater orderUpdater;
    private final OrderReader orderReader;

    public OrderApplicationService(ProductServiceFeign productService,
                                   AuthServiceFeign authServiceFeign,
                                   UserServiceFeign userServiceFeign,
                                   OrderRepository orderRepository,
                                   StripeService stripeService) {
        this.productService = productService;
        this.userServiceFeign = userServiceFeign;
        this.authServiceFeign = authServiceFeign;
        this.cartReader = new CartReader();
        this.orderCreator = new OrderCreator(orderRepository, stripeService);
        this.orderUpdater = new OrderUpdater(orderRepository);
        this.orderReader = new OrderReader(orderRepository);
    }

    @Transactional(readOnly = true)
    public DetailCartResponse getCartDetails(DetailCartRequest detailCartRequest) {
        String publicIds = detailCartRequest
                .items()
                .stream()
                .map(product -> product.productId().value().toString())
                .collect(Collectors.joining(","));
        List<ProductDTO> productsInformation = productService.getProductsByPublicIdsIn(publicIds);
        return cartReader.getDetails(productsInformation);
    }

    @Transactional
    public StripeSessionId createOrder(List<DetailCartItemRequest> items) {
        RestUser authenticatedUser = authServiceFeign.getAuthenticatedUser();
        String publicIds = items
                .stream()
                .map(product -> product.productId().value().toString())
                .collect(Collectors.joining(","));
        List<ProductDTO> productsInformation = productService.getProductsByPublicIdsIn(publicIds);
        return orderCreator.create(productsInformation, items, authenticatedUser);
    }

    @Transactional
    public void updateOrder(StripeSessionInformation stripeSessionInformation) {
        List<OrderedProduct> orderedProducts = this.orderUpdater.updateOrderFromStripe(stripeSessionInformation);
        List<OrderProductQuantity> orderProductQuantities = this.orderUpdater.computeQuantity(orderedProducts);
        this.productService.updateProductQuantity(orderProductQuantities);
        this.userServiceFeign.updateAddress(stripeSessionInformation.userAddress());
    }

    @Transactional(readOnly = true)
    public Page<Order> findOrdersForConnectedUser(Pageable pageable) {
        RestUser authenticatedUser = authServiceFeign.getAuthenticatedUser();
        return orderReader.findAllByUserPublicId(new PublicId(authenticatedUser.publicId()), pageable);
    }

    @Transactional(readOnly = true)
    public Page<Order> findOrdersForAdmin(Pageable pageable) {
        Page<Order> orders = orderReader.findAll(pageable);
        String userPublicIds = orders.getContent()
                .stream().map(order -> order.getUserPublicId().toString())
                .collect(Collectors.joining(","));

        List<RestUser> usersByPublicIds = userServiceFeign.getUsersByPublicIds(userPublicIds);

        for (Order order : orders) {
            if (usersByPublicIds.stream().anyMatch(user -> user.publicId().equals(order.getUserPublicId()))) {
                UserAddress address = usersByPublicIds.stream().filter(user -> user.publicId().equals(order.getUserPublicId())).findFirst().get().address();
                order.updateAddress(address);
            }
        }

        return orders;
    }
}
