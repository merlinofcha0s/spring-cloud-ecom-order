package fr.plb.ecom_order.infrastructure.secondary.service.product.dto;

import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.enums.ProductSize;
import org.jilt.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record ProductDTO(
        String brand,
        String color,
        String description,
        String name,
        long price,
        ProductSize size,
        CategoryDTO category,
        List<PictureDTO> pictures,
        Long dbId,
        boolean featured,
        UUID publicId,
        int nbInStock
) {}
