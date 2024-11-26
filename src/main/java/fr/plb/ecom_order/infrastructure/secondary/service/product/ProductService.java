package fr.plb.ecom_order.infrastructure.secondary.service.product;

import fr.plb.ecom_order.domain.aggregate.OrderProductQuantity;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ProductService {

    // /by-public-ids GET
    List<ProductDTO> getProductsByPublicIdsIn(@RequestParam("publicIds") String publicIds);

    // /update-product-quantity PUT
    void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities);
}
