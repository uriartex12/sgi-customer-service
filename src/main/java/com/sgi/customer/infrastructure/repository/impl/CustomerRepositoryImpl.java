package com.sgi.customer.infrastructure.repository.impl;

import com.sgi.customer.domain.model.Customer;
import com.sgi.customer.domain.ports.out.CustomerRepository;
import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import com.sgi.customer.infrastructure.mapper.CustomerMapper;
import com.sgi.customer.infrastructure.repository.CustomerRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Implementación de {@link CustomerRepository} para la gestión reactiva de clientes.
 * Funciona como intermediario entre la capa de servicio y {@link CustomerRepositoryJpa},
 * realizando operaciones no bloqueantes sobre la base de datos.
 * Utiliza {@link CustomerMapper} para mapear de manera consistente las entidades
 * a objetos de transferencia de datos (DTOs).
 */
@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerRepositoryJpa repositoryJpa;

    @Override
    public Mono<CustomerResponse> save(Customer customer) {
        return repositoryJpa.save(customer)
                .map(CustomerMapper.INSTANCE::toCustomerResponse);
    }

    @Override
    public Mono<Void> delete(Customer customer) {
        return repositoryJpa.delete(customer);
    }

    @Override
    public Flux<CustomerResponse> findAll() {
        return repositoryJpa.findAll()
                .map(CustomerMapper.INSTANCE::toCustomerResponse);
    }

    @Override
    public Mono<Customer> findById(String id) {
        return repositoryJpa.findById(id);
    }
}