package fr.plb.ecom_order.infrastructure.secondary.service.product.dto;


import fr.plb.ecom_order.shared.error.domain.Assert;
import org.jilt.Builder;

@Builder
public record PictureDTO(byte[] file, String mimeType) {

  public PictureDTO {
    Assert.notNull("file", file);
    Assert.notNull("mimeType", mimeType);
  }

}
