package fr.plb.ecom_order.infrastructure.secondary.service.product.dto;

import org.jilt.Builder;

import java.util.UUID;

@Builder
public class ProductCartDTO {

  private String name;

  private long price;

  private String brand;

  private PictureDTO picture;

  private UUID publicId;

  public ProductCartDTO() {
  }

  public ProductCartDTO(String name, long price, String brand, PictureDTO picture, UUID publicId) {
    this.name = name;
    this.price = price;
    this.brand = brand;
    this.picture = picture;
    this.publicId = publicId;
  }

  public static ProductCartDTO from(ProductDTO product) {
    return ProductCartDTOBuilder.productCartDTO()
      .name(product.name())
      .price(product.price())
      .brand(product.brand())
      .picture(product.pictures().stream().findFirst().orElseThrow())
      .publicId(product.publicId())
      .build();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getPrice() {
    return price;
  }

  public void setPrice(long price) {
    this.price = price;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public PictureDTO getPicture() {
    return picture;
  }

  public void setPicture(PictureDTO picture) {
    this.picture = picture;
  }

  public UUID getPublicId() {
    return publicId;
  }

  public void setPublicId(UUID publicId) {
    this.publicId = publicId;
  }
}
