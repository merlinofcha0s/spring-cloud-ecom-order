package fr.plb.ecom_order.infrastructure.primary.order;

import fr.plb.ecom_order.domain.aggregate.Order;
import fr.plb.ecom_order.domain.vo.OrderStatus;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record RestOrderReadAdmin(UUID publicId,
                                 OrderStatus status,
                                 List<RestOrderedItemRead> orderedItems,
                                 String address,
                                 String email) {

    public static RestOrderReadAdmin from(Order order) {
        StringBuilder address = new StringBuilder();
        if (order.getAddress() != null) {
            address.append(order.getAddress().street());
            address.append(", ");
            address.append(order.getAddress().city());
            address.append(", ");
            address.append(order.getAddress().zipCode());
            address.append(", ");
            address.append(order.getAddress().country());
        }

        return RestOrderReadAdminBuilder.restOrderReadAdmin()
                .publicId(order.getPublicId().value())
                .status(order.getStatus())
                .orderedItems(RestOrderedItemRead.from(order.getOrderedProducts()))
                .address(address.toString())
                .email(order.getUserPublicId().toString())
                .build();
    }
}
