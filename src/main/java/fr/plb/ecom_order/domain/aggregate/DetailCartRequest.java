package fr.plb.ecom_order.domain.aggregate;

import org.jilt.Builder;

import java.util.List;

@Builder
public record DetailCartRequest(List<DetailCartItemRequest> items) {
}
