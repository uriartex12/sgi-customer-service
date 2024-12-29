package com.sgi.customer.infrastructure.controller;

import com.sgi.customer.domain.ports.in.CustomerService;
import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller to handle operations related to customers.
 */
@RestController
public class CustomerController implements V1Api {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(Mono<CustomerRequest> customerRequest,
                                                                 ServerWebExchange exchange) {
        return customerService.createCustomer(customerRequest)
                .map(customerResponse -> ResponseEntity.status(HttpStatus.CREATED).body(customerResponse));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String id, ServerWebExchange exchange) {
        return customerService.deleteCustomer(id)
                .map(customerResponse -> ResponseEntity.ok().body(customerResponse));
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getAllCustomers(ServerWebExchange exchange) {
        return Mono.fromSupplier(() -> ResponseEntity.ok().body(customerService.getAllCustomers()));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(String id, ServerWebExchange exchange) {
        return customerService.getCustomerById(id)
                .map(customerResponse -> ResponseEntity.ok().body(customerResponse));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> updateCustomer(String id, Mono<CustomerRequest> customerRequest,
                                                                 ServerWebExchange exchange) {
        return customerService.updateCustomer(id, customerRequest)
                .map(customerResponse -> ResponseEntity.ok().body(customerResponse));
    }
}

