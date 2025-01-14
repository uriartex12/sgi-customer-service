package com.sgi.customer.domain.ports.in;

import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import com.sgi.customer.infrastructure.dto.SummaryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service interface for handling customer-related operations.
 * Provides methods for creating, retrieving, updating, and deleting customers.
 * These methods interact with the underlying customer repository and map
 * entities to appropriate response models.
 */
public interface CustomerService {

    Mono<CustomerResponse> createCustomer(Mono<CustomerRequest> customer);

    Mono<Void> deleteCustomer(String id);

    Flux<CustomerResponse> getAllCustomers();

    Mono<CustomerResponse> getCustomerById(String id);

    Mono<CustomerResponse> updateCustomer(String id, Mono<CustomerRequest> customer);

    Mono<SummaryResponse> getCustomerSummary(String customerId);
}
