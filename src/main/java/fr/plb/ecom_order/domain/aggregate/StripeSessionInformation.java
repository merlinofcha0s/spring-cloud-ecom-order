package fr.plb.ecom_order.domain.aggregate;

import fr.plb.ecom_order.domain.vo.StripeSessionId;
import fr.plb.ecom_order.infrastructure.secondary.service.user.vo.UserAddressToUpdate;
import org.jilt.Builder;

import java.util.List;

@Builder
public record StripeSessionInformation(StripeSessionId stripeSessionId,
                                       UserAddressToUpdate userAddress,
                                       List<OrderProductQuantity> orderProductQuantity) {
}
