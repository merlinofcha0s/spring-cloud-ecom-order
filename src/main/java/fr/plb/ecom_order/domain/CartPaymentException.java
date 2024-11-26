package fr.plb.ecom_order.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CartPaymentException extends RuntimeException {

    private static final Logger log = LoggerFactory.getLogger(CartPaymentException.class);

    public CartPaymentException(String message, Throwable cause) {
        super(message, cause);
        log.error(message, cause);
    }

    public CartPaymentException(Throwable cause) {
        super(cause);
    }
}
