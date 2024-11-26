package fr.plb.ecom_order.domain.aggregate;

import fr.plb.ecom_order.domain.vo.PublicId;
import org.jilt.Builder;

@Builder
public record DetailCartItemRequest(PublicId productId, long quantity) {
}
