
package fr.plb.ecom_order.infrastructure.secondary.service.product;

import fr.plb.ecom_order.configuration.feign.DefaultClientOpenFeignConfiguration;
import fr.plb.ecom_order.domain.aggregate.OrderProductQuantity;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ecom-product", path = "/api/products",
        configuration = DefaultClientOpenFeignConfiguration.class)
public interface ProductServiceFeign {

    @RequestMapping(value = "/by-public-ids", method = RequestMethod.GET)
    List<ProductDTO> getProductsByPublicIdsIn(@RequestParam("publicIds") String publicIds);

    @RequestMapping(value = "/update-product-quantity", method = RequestMethod.PUT)
    void updateProductQuantity(List<OrderProductQuantity> orderProductQuantities);
}
