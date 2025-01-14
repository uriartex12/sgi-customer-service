package com.sgi.customer.application.service;

import com.sgi.customer.domain.model.Customer;
import com.sgi.customer.domain.ports.in.CustomerService;
import com.sgi.customer.domain.ports.out.CustomerRepository;
import com.sgi.customer.domain.ports.out.FeignExternalService;
import com.sgi.customer.domain.shared.CustomError;
import com.sgi.customer.infrastructure.dto.AccountResponse;
import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import com.sgi.customer.infrastructure.dto.SummaryResponse;
import com.sgi.customer.infrastructure.dto.CardResponse;
import com.sgi.customer.infrastructure.dto.CreditResponse;
import com.sgi.customer.infrastructure.dto.SummaryResponseProduct;
import com.sgi.customer.infrastructure.exception.CustomException;
import com.sgi.customer.infrastructure.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static com.sgi.customer.domain.shared.Constants.urlParamsComponentBuilder;

/**
 * Implementation of the CustomerService interface.
 * Provides methods for creating, retrieving, updating, and deleting customer data.
 * Interacts with the repository to persist customer information.
 */
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    @Value("${feign.client.config.account-service.url}")
    private String accountServiceUrl;

    @Value("${feign.client.config.card-service.url}")
    private String cardServiceUrl;

    @Value("${feign.client.config.credit-service.url}")
    private String creditServiceUrl;

    private final CustomerRepository customerRepository;

    private final FeignExternalService webClient;

    @Override
    public Mono<CustomerResponse> createCustomer(Mono<CustomerRequest> customer) {
        return customer.flatMap(customerRequest ->
                CustomerMapper.INSTANCE.map(Mono.just(customerRequest))
                        .flatMap(customerRepository::save));
    }

    @Override
    public Mono<Void> deleteCustomer(String id) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(CustomError.E_CUSTOMER_NOT_FOUND)))
                .flatMap(customerRepository::delete);
    }

    @Override
    public Flux<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Mono<CustomerResponse> getCustomerById(String id) {
        return customerRepository.findById(id)
                .map(CustomerMapper.INSTANCE::toCustomerResponse);
    }

    @Override
    public Mono<CustomerResponse> updateCustomer(String id, Mono<CustomerRequest> customerRequestMono) {
        return customerRepository.findById(id)
                .switchIfEmpty(Mono.error(new CustomException(CustomError.E_CUSTOMER_NOT_FOUND)))
                .flatMap(customer ->
                        customerRequestMono.map(updatedCustomer -> {
                            Customer updatedEntity = CustomerMapper.INSTANCE.toCustomer(updatedCustomer, customer.getId());
                            updatedEntity.setId(customer.getId());
                            return updatedEntity;
                        })
                )
                .flatMap(customerRepository::save);
    }

    @Override
    public Mono<SummaryResponse> getCustomerSummary(String customerId) {
        return customerRepository.findById(customerId)
                .switchIfEmpty(Mono.error(new CustomException(CustomError.E_CUSTOMER_NOT_FOUND)))
                .flatMap(customer -> {
                    Mono<List<AccountResponse>> accountsMono = Flux.from(webClient.get(
                                    urlParamsComponentBuilder(accountServiceUrl, "/v1/accounts", Map.of("clientId", customerId)),
                                    null, AccountResponse.class, true)).collectList();
                    Mono<List<CardResponse>> cardsMono = Flux.from(webClient.get(
                                    urlParamsComponentBuilder(cardServiceUrl, "/v1/cards", Map.of("clientId", customerId)),
                                    null, CardResponse.class, true)).collectList();
                    Mono<List<CreditResponse>> creditsMono = Flux.from(webClient.get(
                                    urlParamsComponentBuilder(creditServiceUrl, "/v1/credits", Map.of("clientId", customerId)),
                                    null, CreditResponse.class, true)).collectList();
                    Mono<SummaryResponseProduct> summaryResponseProductMono = Mono.zip(accountsMono, cardsMono, creditsMono)
                            .map(tuple -> {
                                List<AccountResponse> accounts = tuple.getT1();
                                List<CardResponse> cards = tuple.getT2();
                                List<CreditResponse> credits = tuple.getT3();
                                SummaryResponseProduct product = new SummaryResponseProduct();
                                product.accounts(accounts);
                                product.cards(cards);
                                product.credits(credits);
                                return product;
                            });
                    return summaryResponseProductMono.map(product ->
                            new SummaryResponse(customer.getId(), customer.getName(), product));
                });
    }





}
