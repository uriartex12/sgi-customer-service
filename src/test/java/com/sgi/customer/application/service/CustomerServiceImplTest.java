package com.sgi.customer.application.service;

import com.sgi.customer.domain.model.Customer;
import com.sgi.customer.domain.ports.out.CustomerRepository;
import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import com.sgi.customer.infrastructure.exception.CustomException;
import com.sgi.customer.infrastructure.helper.FactoryTest;
import com.sgi.customer.infrastructure.mapper.CustomerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

/**
 * Unit tests for {@link CustomerServiceImpl}.
 * This class tests the business logic of the {@link CustomerServiceImpl} class.
 * It covers methods such as creating, deleting, updating, and retrieving customers.
 */
@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void createCustomer_shouldReturnCreatedResponse() {
        CustomerRequest customerRequest = FactoryTest.toFactoryCustomer(CustomerRequest.class);
        CustomerResponse customerResponse = FactoryTest.toFactoryCustomer(CustomerResponse.class);
        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(customerResponse));

        Mono<CustomerResponse> result = customerService.createCustomer(Mono.just(customerRequest));

        StepVerifier.create(result)
                .expectNext(customerResponse)
                .verifyComplete();

        verify(customerRepository, times(1)).save(any(Customer.class));

    }

    @Test
    void deleteCustomer_shouldDeleteExistingCustomer() {
        String customerId = UUID.randomUUID().toString();
        Customer customer = FactoryTest.toFactoryEntityCustomer();

        when(customerRepository.findById(customerId)).thenReturn(Mono.just(customer));
        when(customerRepository.delete(customer)).thenReturn(Mono.empty());

        Mono<Void> result = customerService.deleteCustomer(customerId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(customerRepository).findById(customerId);
        verify(customerRepository).delete(customer);
    }

    @Test
    void deleteCustomer_shouldThrowExceptionIfCustomerNotFound() {
        String customerId = UUID.randomUUID().toString();
        Mockito.when(customerRepository.findById(customerId)).thenReturn(Mono.empty());

        Mono<Void> result = customerService.deleteCustomer(customerId);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof CustomException
                        && ((CustomException) throwable).getCode().equals("CUSTOMER-001"))
                .verify();

        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).delete(any(Customer.class));
    }

    @Test
    void getAllCustomers_shouldReturnAllCustomers() {
        CustomerResponse customerResponse1 = FactoryTest.toFactoryCustomer(CustomerResponse.class);
        CustomerResponse customerResponse2 = FactoryTest.toFactoryCustomer(CustomerResponse.class);

        when(customerRepository.findAll()).thenReturn(Flux.just(customerResponse1, customerResponse2));

        Flux<CustomerResponse> result = customerService.getAllCustomers();

        StepVerifier.create(result)
                .expectNext(customerResponse1, customerResponse2)
                .verifyComplete();

        verify(customerRepository).findAll();
    }


    @Test
    void getCustomerById_shouldReturnCustomerIfFound() {
        String customerId =  UUID.randomUUID().toString();
        Customer customer = FactoryTest.toFactoryEntityCustomer();
        when(customerRepository.findById(customerId)).thenReturn(Mono.just(customer));
        Mono<CustomerResponse> result = customerService.getCustomerById(customerId);
        StepVerifier.create(result)
                .expectNext(CustomerMapper.INSTANCE.toCustomerResponse(customer))
                .verifyComplete();

        verify(customerRepository).findById(customerId);
    }

    @Test
    void getCustomerById_shouldReturnEmptyIfNotFound() {
        String customerId =  UUID.randomUUID().toString();
        when(customerRepository.findById(customerId)).thenReturn(Mono.empty());

        Mono<CustomerResponse> result = customerService.getCustomerById(customerId);

        StepVerifier.create(result)
                .verifyComplete();

        verify(customerRepository).findById(customerId);
    }

    @Test
    void updateCustomer_shouldUpdateExistingCustomer() {
        String customerId =  UUID.randomUUID().toString();
        Customer customer = FactoryTest.toFactoryEntityCustomer();
        customer.setId(customerId);
        CustomerRequest updatedRequest = FactoryTest.toFactoryCustomer(CustomerRequest.class);
        CustomerResponse updatedResponse = FactoryTest.toFactoryCustomer(CustomerResponse.class);
        when(customerRepository.findById(customerId)).thenReturn(Mono.just(customer));
        when(customerRepository.save(any(Customer.class))).thenReturn(Mono.just(updatedResponse));

        Mono<CustomerResponse> result = customerService.updateCustomer(customerId, Mono.just(updatedRequest));
        StepVerifier.create(result)
                .expectNext(updatedResponse)
                .verifyComplete();

        verify(customerRepository).findById(customerId);
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void updateCustomer_shouldThrowExceptionIfCustomerNotFound() {
        String customerId =  UUID.randomUUID().toString();
        CustomerRequest updatedRequest = FactoryTest.toFactoryCustomer(CustomerRequest.class);

        when(customerRepository.findById(customerId)).thenReturn(Mono.empty());

        Mono<CustomerResponse> result = customerService.updateCustomer(customerId, Mono.just(updatedRequest));

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof CustomException
                        && ((CustomException) throwable).getCode().equals("CUSTOMER-001"))
                .verify();

        verify(customerRepository).findById(customerId);
        verify(customerRepository, never()).save(any(Customer.class));
    }


}
