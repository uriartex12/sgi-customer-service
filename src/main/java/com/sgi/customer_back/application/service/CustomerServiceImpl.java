package com.sgi.customer_back.application.service;

import com.sgi.customer_back.domain.ports.in.CustomerService;
import com.sgi.customer_back.domain.ports.out.CustomerRepository;
import com.sgi.customer_back.infrastructure.dto.CustomerRequest;
import com.sgi.customer_back.infrastructure.dto.CustomerResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> createCustomer(Mono<CustomerRequest> customerMono) {
        return  customerRepository.createCustomer(customerMono)
                .map(createdCustomer ->ResponseEntity.ok().body(createdCustomer))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @Override
    public Mono<ResponseEntity<Void>> deleteCustomer(String id) {
        return customerRepository.deleteCustomer(id)
                .map(updateCustomer-> ResponseEntity.ok().body(updateCustomer))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @Override
    public Mono<ResponseEntity<Flux<CustomerResponse>>> getAllCustomers() {
        return Mono.fromSupplier(() -> ResponseEntity.ok().body(customerRepository.getAllCustomers()))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(String id) {
        return  customerRepository.getCustomerById(id)
                .map(createdCustomer ->ResponseEntity.ok().body(createdCustomer))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @Override
    public Mono<ResponseEntity<CustomerResponse>> updateCustomer(String id, Mono<CustomerRequest> customer) {
        return  customerRepository.updateCustomer(id,customer)
                .map(createdCustomer ->ResponseEntity.ok().body(createdCustomer))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));

    }
}
