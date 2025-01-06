package com.sgi.customer.infrastructure.helper;

import com.sgi.customer.domain.model.Customer;
import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;


/**
 * Class containing methods to generate CustomerRequest and CustomerResponse objects
 * with default values to facilitate unit testing.
 */
public class FactoryTest {

    /**
     * Generates an CustomerRequest object with default values for testing.
     *
     * @return An CustomerRequest object with configured values for testing.
     */
    @SneakyThrows
    public static <R> R toFactoryCustomer(Class<R> response) {
        R account = response.getDeclaredConstructor().newInstance();
        if (account instanceof CustomerRequest) {
            return (R) initializeAccount((CustomerRequest) account);
        } else if (account instanceof CustomerResponse) {
            return (R) initializeAccount((CustomerResponse) account);
        }
        return account;
    }

    private static CustomerRequest initializeAccount(CustomerRequest customer) {
        customer.setAddress("ADDRESS-test");
        customer.setType(CustomerRequest.TypeEnum.PERSONAL);
        customer.setName("TEST - customer");
        customer.setProfile(CustomerRequest.ProfileEnum.VIP);
        customer.setDocumentId("82782345");
        customer.setPhoneNumber("5555555555555");
        return customer;
    }

    private static CustomerResponse initializeAccount(CustomerResponse customer) {
        customer.setAddress("ADDRESS-test");
        customer.setType(CustomerResponse.TypeEnum.PERSONAL);
        customer.setName("TEST - customer");
        customer.setProfile(CustomerResponse.ProfileEnum.VIP);
        customer.setDocumentId("82782345");
        customer.setPhoneNumber("5555555555555");
        return customer;
    }

    /**
     * Generates a list of CustomerResponse objects with predefined values for testing purposes.
     *
     * @return A list of CustomerResponse objects with default data.
     */
    public static List<CustomerResponse> toFactoryListCustomers() {
        CustomerResponse customerOne = new CustomerResponse();
        customerOne.setAddress("ADDRESS-test");
        customerOne.setType(CustomerResponse.TypeEnum.PERSONAL);
        customerOne.setName("TEST - customer");
        customerOne.setProfile(CustomerResponse.ProfileEnum.VIP);
        customerOne.setDocumentId("82782345");
        customerOne.setPhoneNumber("5555555555555");
        customerOne.setId(UUID.randomUUID().toString());

        CustomerResponse customerTwo = new CustomerResponse();
        customerTwo.setId(UUID.randomUUID().toString());
        customerTwo.setAddress("ADDRESS-test");
        customerTwo.setType(CustomerResponse.TypeEnum.PERSONAL);
        customerTwo.setName("TEST - customer");
        customerTwo.setProfile(CustomerResponse.ProfileEnum.VIP);
        customerTwo.setDocumentId("82782345");
        customerTwo.setPhoneNumber("5555555555555");
        return List.of(customerOne, customerTwo);

    }

    /**
     * Creates a new Customer object with predefined values for testing purposes.
     *
     * @return A Customer object populated with random and default values, such as a Customer ID, address,
     *         name, and other attributes.
     */
    public static Customer toFactoryEntityCustomer() {
        return Customer.builder()
                .id(randomUUID().toString())
                .address("ADDRESS-test")
                .type("PERSONAL")
                .name("TEST - customer")
                .profile("VIP")
                .documentId("82782345")
                .phoneNumber("5555555555555")
                .build();
    }



}
