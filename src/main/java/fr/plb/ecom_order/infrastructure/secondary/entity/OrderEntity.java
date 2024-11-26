package fr.plb.ecom_order.infrastructure.secondary.entity;


import fr.plb.ecom_order.domain.aggregate.Order;
import fr.plb.ecom_order.domain.aggregate.OrderBuilder;
import fr.plb.ecom_order.domain.aggregate.OrderedProduct;
import fr.plb.ecom_order.domain.vo.OrderStatus;
import fr.plb.ecom_order.domain.vo.PublicId;
import fr.plb.ecom_order.shared.jpa.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.jilt.Builder;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "order")
@Builder
public class OrderEntity extends AbstractAuditingEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequenceGenerator")
    @SequenceGenerator(name = "orderSequenceGenerator", sequenceName = "order_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "public_id", nullable = false, unique = true)
    private UUID publicId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "stripe_session_id", nullable = false)
    private String stripeSessionId;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.REMOVE)
    private Set<OrderedProductEntity> orderedProducts = new HashSet<>();

    @Column(name = "customer_public_id")
    private UUID userPublicId;

    public OrderEntity() {
    }


    public OrderEntity(Long id, UUID publicId, OrderStatus status, String stripeSessionId, Set<OrderedProductEntity> orderedProducts, UUID userPublicId) {
        this.id = id;
        this.publicId = publicId;
        this.status = status;
        this.stripeSessionId = stripeSessionId;
        this.orderedProducts = orderedProducts;
        this.userPublicId = userPublicId;
    }

    public static OrderEntity from(Order order) {
        Set<OrderedProductEntity> orderedProductEntities = order.getOrderedProducts()
                .stream().map(OrderedProductEntity::from).collect(Collectors.toSet());

        return OrderEntityBuilder.orderEntity()
                .publicId(order.getPublicId().value())
                .status(order.getStatus())
                .userPublicId(order.getUserPublicId())
                .stripeSessionId(order.getStripeId())
                .orderedProducts(orderedProductEntities)
                .build();
    }

    public static Order toDomain(OrderEntity orderEntity) {
        Set<OrderedProduct> orderedProducts = orderEntity.getOrderedProducts().stream()
                .map(OrderedProductEntity::toDomain).collect(Collectors.toSet());

        return OrderBuilder.order()
                .publicId(new PublicId(orderEntity.getPublicId()))
                .status(orderEntity.getStatus())
                .userPublicId(orderEntity.getUserPublicId())
                .stripeId(orderEntity.getStripeSessionId())
                .orderedProducts(orderedProducts.stream().toList())
                .build();
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getStripeSessionId() {
        return stripeSessionId;
    }

    public void setStripeSessionId(String stripeSessionId) {
        this.stripeSessionId = stripeSessionId;
    }

    public Set<OrderedProductEntity> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(Set<OrderedProductEntity> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public UUID getUserPublicId() {
        return userPublicId;
    }

    public void setUserPublicId(UUID userPublicId) {
        this.userPublicId = userPublicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderEntity order)) return false;
        return Objects.equals(publicId, order.publicId) && status == order.status && Objects.equals(stripeSessionId, order.stripeSessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(publicId, status, stripeSessionId);
    }
}
