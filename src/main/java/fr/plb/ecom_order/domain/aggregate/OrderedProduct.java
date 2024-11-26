package fr.plb.ecom_order.domain.aggregate;

import fr.plb.ecom_order.domain.vo.OrderPrice;
import fr.plb.ecom_order.domain.vo.OrderQuantity;
import fr.plb.ecom_order.domain.vo.ProductPublicId;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.product.vo.ProductName;
import org.jilt.Builder;

@Builder
public record OrderedProduct(ProductPublicId productPublicId, OrderPrice price, OrderQuantity quantity,
                             ProductName productName) {

    public static OrderedProduct create(long quantity, ProductDTO product) {
        return OrderedProductBuilder.orderedProduct()
                .price(new OrderPrice(product.price()))
                .quantity(new OrderQuantity(quantity))
                .productName(new ProductName(product.name()))
                .productPublicId(new ProductPublicId(product.publicId()))
                .build();
    }

}
