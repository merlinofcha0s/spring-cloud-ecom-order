package fr.plb.ecom_order.domain.aggregate;

import fr.plb.ecom_order.domain.vo.OrderQuantity;
import fr.plb.ecom_order.domain.vo.ProductPublicId;
import org.jilt.Builder;

@Builder
public record OrderProductQuantity(OrderQuantity quantity, ProductPublicId productPublicId) {
}
