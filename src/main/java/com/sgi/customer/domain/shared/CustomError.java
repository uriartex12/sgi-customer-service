package com.sgi.customer.domain.shared;

import com.sgi.customer.infrastructure.exception.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Enum representing custom errors for the Customer-service application.
 * Each constant includes an error code, message, and HTTP status for specific errors.
 */
@Getter
@AllArgsConstructor
public enum CustomError {

    E_CUSTOMER_NOT_FOUND(new ApiError(HttpStatus.NOT_FOUND, "CUSTOMER-001", "Customer not found")),
    E_MALFORMED_ACCOUNT_DATA(new ApiError(HttpStatus.BAD_REQUEST, "CUSTOMER-002", "Malformed account data"));

    private final ApiError error;
}
