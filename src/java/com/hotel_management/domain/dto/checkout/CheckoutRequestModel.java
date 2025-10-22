package com.hotel_management.domain.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Request model for processing checkout
 * @author thuannd.dev
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckoutRequestModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int bookingId;
    private String paymentMethod; // Cash, Credit Card, Debit Card, Online
}


