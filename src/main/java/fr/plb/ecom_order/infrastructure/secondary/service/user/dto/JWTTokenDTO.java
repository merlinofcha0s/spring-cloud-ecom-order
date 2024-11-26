package fr.plb.ecom_order.infrastructure.secondary.service.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record JWTTokenDTO(@JsonProperty("id_token") String idToken) {
}
