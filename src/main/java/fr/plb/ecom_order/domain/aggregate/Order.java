package fr.plb.ecom_order.domain.aggregate;

import fr.plb.ecom_order.domain.vo.OrderStatus;
import fr.plb.ecom_order.domain.vo.PublicId;
import fr.plb.ecom_order.domain.vo.StripeSessionId;
import fr.plb.ecom_order.infrastructure.secondary.service.user.vo.UserAddress;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public class Order {

    private OrderStatus status;

    private UUID userPublicId;

    private String stripeId;

    private PublicId publicId;

    private List<OrderedProduct> orderedProducts;

    private UserAddress address;

    public Order(OrderStatus status, UUID userPublicId, String stripeId, PublicId publicId,
                 List<OrderedProduct> orderedProducts, UserAddress address) {
        this.status = status;
        this.userPublicId = userPublicId;
        this.stripeId = stripeId;
        this.publicId = publicId;
        this.orderedProducts = orderedProducts;
        this.address = address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public UUID getUserPublicId() {
        return userPublicId;
    }

    public String getStripeId() {
        return stripeId;
    }

    public PublicId getPublicId() {
        return publicId;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public static Order create(UUID userPublicId, List<OrderedProduct> orderedProducts,
                               StripeSessionId stripeSessionId, UserAddress address) {
        return OrderBuilder.order()
                .publicId(new PublicId(UUID.randomUUID()))
                .userPublicId(userPublicId)
                .status(OrderStatus.PENDING)
                .address(address)
                .orderedProducts(orderedProducts)
                .stripeId(stripeSessionId.value())
                .build();
    }

    public void validatePayment() {
        this.status = OrderStatus.PAID;
    }

    public void updateAddress(UserAddress address) {
        this.address = address;
    }

    public UserAddress getAddress() {
        return address;
    }
}
