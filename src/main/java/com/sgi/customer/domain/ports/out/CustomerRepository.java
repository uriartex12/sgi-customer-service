package com.sgi.customer.domain.ports.out;
import com.sgi.customer.domain.model.Customer;
import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Repository interface defining operations to manage customers.
 */
public interface CustomerRepository {
    /**
     * Saves a Customer in the repository.
     *
     * @param customer the credit information to save
     * @return a Mono containing the saved customer response
     */
    Mono<CustomerResponse> save(Customer customer);
    /**
     * Deletes a customer from the repository.
     *
     * @param customer the customer information to delete
     * @return a Mono representing the deletion operation
     */
    Mono<Void> delete(Customer customer);
    /**
     * Retrieves all available customers.
     *
     * @return a Flux containing all credit responses
     */
    Flux<CustomerResponse> findAll();
    /**
     * Finds a Customer by its ID.
     *
     * @param id the unique identifier of the customer
     * @return a Mono containing the found Transaction or empty if not found
     */
    Mono<Customer> findById(String id);
}
