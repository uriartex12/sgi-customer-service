package com.sgi.customer.domain.ports.in;

import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for handling customer-related operations.
 * Provides methods for creating, retrieving, updating, and deleting customers.
 * These methods interact with the underlying customer repository and map
 * entities to appropriate response models.
 */
public interface CustomerService {

    /**
     * Creates a new customer based on the provided customer request.
     *
     * @param customer The customer request data.
     * @return A Mono containing the created customer response.
     */
    Mono<CustomerResponse> createCustomer(Mono<CustomerRequest> customer);

    /**
     * Deletes a customer by their unique identifier.
     *
     * @param id The ID of the customer to delete.
     * @return A Mono that completes once the customer is deleted.
     */
    Mono<Void> deleteCustomer(String id);

    /**
     * Retrieves all customers in the system.
     *
     * @return A Flux containing all customer responses.
     */
    Flux<CustomerResponse> getAllCustomers();

    /**
     * Retrieves a customer by their unique identifier.
     *
     * @param id The ID of the customer to retrieve.
     * @return A Mono containing the customer response.
     */
    Mono<CustomerResponse> getCustomerById(String id);

    /**
     * Updates an existing customer with the provided data.
     *
     * @param id The ID of the customer to update.
     * @param customer The updated customer data.
     * @return A Mono containing the updated customer response.
     */
    Mono<CustomerResponse> updateCustomer(String id, Mono<CustomerRequest> customer);
}
