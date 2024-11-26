package fr.plb.ecom_order.infrastructure.secondary.service.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import fr.plb.ecom_order.domain.CartPaymentException;
import fr.plb.ecom_order.domain.aggregate.DetailCartItemRequest;
import fr.plb.ecom_order.infrastructure.secondary.service.product.dto.ProductDTO;
import fr.plb.ecom_order.infrastructure.secondary.service.user.dto.RestUser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StripeService {

    @Value("${application.stripe.api-key}")
    private String apiKey;

    @Value("${application.client-base-url}")
    private String clientBaseUrl;

    public StripeService() {
    }

    @PostConstruct
    public void setApiKey() {
        Stripe.apiKey = apiKey;
    }

    public Session createPayment(RestUser connectedUser,
                                         List<ProductDTO> productsInformation,
                                         List<DetailCartItemRequest> items) {
        SessionCreateParams.Builder sessionBuilder = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .putMetadata("user_public_id", connectedUser.publicId().toString())
                .setCustomerEmail(connectedUser.email())
                .setBillingAddressCollection(SessionCreateParams.BillingAddressCollection.REQUIRED)
                .setSuccessUrl(this.clientBaseUrl + "/cart/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(this.clientBaseUrl + "/cart/failure");

        for (DetailCartItemRequest itemRequest : items) {
            ProductDTO productDetails = productsInformation.stream()
                    .filter(product -> product.publicId().equals(itemRequest.productId().value()))
                    .findFirst().orElseThrow();

            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .putMetadata("product_id", productDetails.publicId().toString())
                    .setName(productDetails.name())
                    .build();

            SessionCreateParams.LineItem.PriceData linePriceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setUnitAmountDecimal(BigDecimal.valueOf(Double.valueOf(productDetails.price()).longValue() * 100))
                    .setProductData(productData)
                    .setCurrency("EUR")
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(itemRequest.quantity())
                    .setPriceData(linePriceData)
                    .build();

            sessionBuilder.addLineItem(lineItem);
        }

        return createSession(sessionBuilder.build());
    }

    private Session createSession(SessionCreateParams sessionInformation) {
        try {
            return Session.create(sessionInformation);
        } catch (StripeException se) {
            throw new CartPaymentException("Error while creating Stripe session", se);
        }
    }
}
