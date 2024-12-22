package com.sgi.customer_back.domain.ports.out;
import com.sgi.customer_back.infrastructure.dto.CustomerRequest;
import com.sgi.customer_back.infrastructure.dto.CustomerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerRepository {
    Mono<CustomerResponse> createCustomer(Mono<CustomerRequest> customer);
    Mono<Void> deleteCustomer(String id);
    Flux<CustomerResponse> getAllCustomers();
    Mono<CustomerResponse> getCustomerById(String id);
    Mono<CustomerResponse> updateCustomer(String id, Mono<CustomerRequest> customer);
}
