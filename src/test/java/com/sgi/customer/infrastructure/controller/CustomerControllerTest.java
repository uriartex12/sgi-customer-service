package com.sgi.customer.infrastructure.controller;

import com.sgi.customer.domain.model.Customer;
import com.sgi.customer.domain.ports.in.CustomerService;
import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import com.sgi.customer.infrastructure.helper.FactoryTest;
import com.sgi.customer.infrastructure.mapper.CustomerMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.eq;

/**
 * Unit test class for the CustomerController class.
 * This class contains tests to validate the functionality of the CustomerController.
 * It uses Mockito for mocking dependencies and JUnit 5 for running tests.
 */
@WebFluxTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void createCustomer_shouldReturnCustomerResponse() {
        CustomerResponse customerResponse = FactoryTest.toFactoryCustomer(CustomerResponse.class);
        Mockito.when(customerService.createCustomer(any(Mono.class)))
                .thenReturn(Mono.just(customerResponse));
        webTestClient.post()
                .uri("/v1/customers")
                .bodyValue(FactoryTest.toFactoryCustomer(CustomerRequest.class))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CustomerResponse.class)
                .consumeWith(customerResponseEntityExchangeResult -> {
                    CustomerResponse actual = customerResponseEntityExchangeResult.getResponseBody();
                    Assertions.assertNotNull(actual.getDocumentId());
                })
                .returnResult();
        Mockito.verify(customerService, times(1)).createCustomer(any(Mono.class));
    }

    @Test
    void deleteCustomer_shouldReturnOkResponse() {
        String customerId = randomUUID().toString();
        Mockito.when(customerService.deleteCustomer(customerId)).thenReturn(Mono.empty());
        webTestClient.delete()
                .uri("/v1/customers/{id}", customerId)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void getAllCustomers_shouldReturnFluxOfCustomerResponse() {
        List<CustomerResponse> customers =  FactoryTest.toFactoryListCustomers();
        Flux<CustomerResponse> accountsFlux = Flux.fromIterable(customers);
        Mockito.when(customerService.getAllCustomers()).thenReturn(accountsFlux);
        webTestClient.get()
                .uri("/v1/customers")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerResponse.class)
                .value(list -> assertThat(list).hasSize(2));
    }

    @Test
    void getCustomerById_shouldReturnCustomerResponse() {
        CustomerResponse customerResponse = FactoryTest.toFactoryCustomer(CustomerResponse.class);
        customerResponse.setId(UUID.randomUUID().toString());
        Mockito.when(customerService.getCustomerById(customerResponse.getId()))
                .thenReturn(Mono.just(customerResponse));
        webTestClient.get()
                .uri("/v1/customers/{customerId}", customerResponse.getId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerResponse.class)
                .consumeWith(System.out::println)
                .value(actual -> {
                    Assertions.assertEquals(customerResponse.getId(), actual.getId());
                    Assertions.assertEquals(customerResponse.getDocumentId(), actual.getDocumentId());
                });
    }

    @Test
    void updateCustomer_shouldReturnCustomerResponse() {
        String customerId = UUID.randomUUID().toString();
        Customer customer = FactoryTest.toFactoryEntityCustomer();
        customer.setId(customerId);
        CustomerRequest customerRequest = FactoryTest.toFactoryCustomer(CustomerRequest.class);
        CustomerResponse customerResponse = CustomerMapper.INSTANCE.toCustomerResponse(customer);

        Mockito.when(customerService.updateCustomer(eq(customerId), any(Mono.class)))
                .thenReturn(Mono.just(customerResponse));

        webTestClient.put()
                .uri("/v1/customers/{customerId}", customerId)
                .bodyValue(customerRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(CustomerResponse.class);
        Mockito.verify(customerService, times(1)).updateCustomer(eq(customerId), any(Mono.class));
    }

}
