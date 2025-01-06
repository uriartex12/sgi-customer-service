package com.sgi.customer.infrastructure.repository;

import com.sgi.customer.domain.model.Customer;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import com.sgi.customer.infrastructure.helper.FactoryTest;
import com.sgi.customer.infrastructure.mapper.CustomerMapper;
import com.sgi.customer.infrastructure.repository.impl.CustomerRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

/**
 * Unit tests for {@link CustomerRepositoryImpl}.
 * This class tests the behavior of the repository implementation
 * by using mocks to simulate database interactions.
 */
@ExtendWith(MockitoExtension.class)
public class CustomerRepositoryImplTest {

    @InjectMocks
    private CustomerRepositoryImpl customerRepository;

    @Mock
    private CustomerRepositoryJpa repositoryJpa;

    @Test
    public void testSave() {
        Customer customer = FactoryTest.toFactoryEntityCustomer();
        CustomerResponse accountResponse = CustomerMapper.INSTANCE.toCustomerResponse(customer);
        when(repositoryJpa.save(customer)).thenReturn(Mono.just(customer));
        Mono<CustomerResponse> result = customerRepository.save(customer);
        StepVerifier.create(result)
                .expectNext(accountResponse)
                .verifyComplete();

        verify(repositoryJpa, times(1)).save(customer);
    }

    @Test
    public void testFindById() {
        String customerId = UUID.randomUUID().toString();
        Customer customer = FactoryTest.toFactoryEntityCustomer();
        when(repositoryJpa.findById(customerId))
                .thenReturn(Mono.just(customer));
        Mono<Customer> result = customerRepository.findById(customerId);
        StepVerifier.create(result)
                .expectNext(customer)
                .verifyComplete();

        verify(repositoryJpa, times(1)).findById(customerId);
    }

    @Test
    public void testFindAll() {
        Customer customer1 = FactoryTest.toFactoryEntityCustomer();
        Customer customer2 = FactoryTest.toFactoryEntityCustomer();
        when(repositoryJpa.findAll()).thenReturn(Flux.just(customer1, customer2));
        Flux<CustomerResponse> result = customerRepository.findAll();
        result.collectList().subscribe(responses -> {
            assertNotNull(responses);
            assertEquals(2, responses.size());
        });

        verify(repositoryJpa, times(1)).findAll();
    }

    @Test
    public void testDelete() {
        Customer customer = FactoryTest.toFactoryEntityCustomer();
        customer.setId(UUID.randomUUID().toString());
        when(repositoryJpa.delete(customer)).thenReturn(Mono.empty());
        Mono<Void> result = customerRepository.delete(customer);
        StepVerifier.create(result)
                .verifyComplete();
        verify(repositoryJpa, times(1)).delete(customer);
    }

}
