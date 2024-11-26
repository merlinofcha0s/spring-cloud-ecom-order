package fr.plb.ecom_order.infrastructure.secondary.service.user.dto;

import fr.plb.ecom_order.infrastructure.secondary.service.user.vo.UserAddress;
import org.jilt.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record RestUser(UUID publicId,
                       String firstName,
                       String lastName,
                       String email,
                       String imageUrl,
                       Set<String> authorities,
                       UserAddress address) {
}
