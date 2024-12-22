package com.sgi.customer_back.infrastructure.repository.impl;

import com.sgi.customer_back.domain.model.CustomerEntity;
import com.sgi.customer_back.domain.ports.out.CustomerRepository;
import com.sgi.customer_back.infrastructure.dto.CustomerRequest;
import com.sgi.customer_back.infrastructure.dto.CustomerResponse;
import com.sgi.customer_back.infrastructure.mapper.CustomerMapper;
import com.sgi.customer_back.infrastructure.repository.CustomerRepositoryJPA;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerRepositoryJPA customerRepository;

    public CustomerRepositoryImpl(CustomerRepositoryJPA customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Mono<CustomerResponse> createCustomer(Mono<CustomerRequest> customer) {
        return customer.flatMap(cust ->
                CustomerMapper.INSTANCE.map(Mono.just(cust))
                        .flatMap(customerRepository::save)
        ).map(CustomerMapper.INSTANCE::map);
    }

    @Override
    public Mono<Void> deleteCustomer(String id) {
        return customerRepository.findById(id)
                .flatMap(customerRepository::delete)
                .switchIfEmpty(Mono.error(new Exception("Customer not found")));
    }

    @Override
    public Flux<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .map(CustomerMapper.INSTANCE::map);
    }

    @Override
    public Mono<CustomerResponse> getCustomerById(String id) {
        return customerRepository.findById(id)
                .map(CustomerMapper.INSTANCE::map);
    }

    @Override
    public Mono<CustomerResponse> updateCustomer(String id, Mono<CustomerRequest> customer) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new Exception("Customer not found")))
                .flatMap(customerEntity ->
                        customer.map(updatedCustomer -> {
                            CustomerEntity updatedEntity = CustomerMapper.INSTANCE.map(updatedCustomer,customerEntity.getId());
                            updatedEntity.setId(customerEntity.getId());
                            return updatedEntity;
                        })
                )
                .flatMap(customerRepository::save)
                .map(CustomerMapper.INSTANCE::map);
    }
}