package com.sgi.customer_back.domain.ports.in;

import com.sgi.customer_back.infrastructure.dto.CustomerRequest;
import com.sgi.customer_back.infrastructure.dto.CustomerResponse;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface CustomerService {
    Mono<ResponseEntity<CustomerResponse>> createCustomer(Mono<CustomerRequest> customer);
    Mono<ResponseEntity<Void>> deleteCustomer(String id);
    Mono<ResponseEntity<Flux<CustomerResponse>>> getAllCustomers();
    Mono<ResponseEntity<CustomerResponse>> getCustomerById(String id);
    Mono<ResponseEntity<CustomerResponse>> updateCustomer(String id, Mono<CustomerRequest> customer);
}
