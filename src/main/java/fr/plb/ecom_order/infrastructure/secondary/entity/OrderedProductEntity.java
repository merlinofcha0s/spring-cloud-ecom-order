package fr.plb.ecom_order.infrastructure.secondary.entity;


import fr.plb.ecom_order.domain.aggregate.OrderedProduct;
import fr.plb.ecom_order.domain.aggregate.OrderedProductBuilder;
import fr.plb.ecom_order.domain.vo.OrderPrice;
import fr.plb.ecom_order.domain.vo.OrderQuantity;
import fr.plb.ecom_order.domain.vo.ProductPublicId;
import fr.plb.ecom_order.infrastructure.secondary.service.product.vo.ProductName;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.jilt.Builder;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ordered_product")
@Builder
public class OrderedProductEntity {

  @EmbeddedId
  private OrderedProductEntityPk id;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "quantity", nullable = false)
  private long quantity;

  @Column(name = "product_name", nullable = false)
  private String productName;

  public OrderedProductEntity() {
  }

  public OrderedProductEntity(OrderedProductEntityPk id, Double price, long quantity, String productName) {
    this.id = id;
    this.price = price;
    this.quantity = quantity;
    this.productName = productName;
  }

  public static OrderedProductEntity from(OrderedProduct orderedProduct) {
    OrderedProductEntityPk compositeId = OrderedProductEntityPkBuilder.orderedProductEntityPk()
      .productPublicId(orderedProduct.productPublicId().value())
      .build();

    return OrderedProductEntityBuilder.orderedProductEntity()
      .price(orderedProduct.price().value())
      .quantity(orderedProduct.quantity().value())
      .productName(orderedProduct.productName().value())
      .id(compositeId)
      .build();
  }

  public static List<OrderedProductEntity> from(List<OrderedProduct> orderedProducts) {
    return orderedProducts.stream().map(OrderedProductEntity::from).toList();
  }

  public static OrderedProduct toDomain(OrderedProductEntity orderedProductEntity) {
    return OrderedProductBuilder.orderedProduct()
      .productPublicId(new ProductPublicId(orderedProductEntity.getId().getProductPublicId()))
      .quantity(new OrderQuantity(orderedProductEntity.getQuantity()))
      .price(new OrderPrice(orderedProductEntity.getPrice()))
      .productName(new ProductName(orderedProductEntity.getProductName()))
      .build();
  }

  public OrderedProductEntityPk getId() {
    return id;
  }

  public void setId(OrderedProductEntityPk id) {
    this.id = id;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OrderedProductEntity that)) return false;
    return quantity == that.quantity && Objects.equals(price, that.price) && Objects.equals(productName, that.productName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(price, quantity, productName);
  }
}
