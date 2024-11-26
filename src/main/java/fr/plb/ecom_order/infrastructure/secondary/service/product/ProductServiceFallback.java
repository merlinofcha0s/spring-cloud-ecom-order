package fr.plb.ecom_order.infrastructure.secondary.service.product;

import fr.plb.ecom_order.domain.aggregate.OrderProductQuantity;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductDTO;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceFallback implements ProductServiceFeign {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ProductServiceFallback.class);

    @Override
    public List<ProductDTO> getProductsByPublicIdsIn(String publicIds) {
        log.error("Fallback success");
        return List.of();
    }

    @Override
    public void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities) {

    }
}
