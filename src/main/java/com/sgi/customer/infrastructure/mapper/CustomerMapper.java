package com.sgi.customer.infrastructure.mapper;

import com.sgi.customer.domain.model.Customer;
import com.sgi.customer.infrastructure.dto.CustomerRequest;
import com.sgi.customer.infrastructure.dto.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Mapper interface for converting between Customer domain objects and DTOs.
 * Provides methods for mapping CustomerRequest and CustomerResponse to and from Customer.
 */
@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    /**
     * Maps a Customer domain object to a CustomerResponse DTO.
     *
     * @param customer the Customer domain object.
     * @return the corresponding CustomerResponse DTO.
     */
    @Mapping(target = "type", source = "type")
    CustomerResponse toCustomerResponse(Customer customer);

    /**
     * Maps a CustomerRequest DTO to a Customer domain object with a provided ID.
     *
     * @param customer the CustomerRequest DTO.
     * @param id       the ID to set in the resulting Customer domain object.
     * @return the resulting Customer domain object.
     */
    @Mapping(target = "id", ignore = true)
    Customer toCustomer(CustomerRequest customer, String id);

    /**
     * Creates a new Customer domain object from a CustomerRequest DTO.
     * Generates a unique UUID for the Customer ID.
     *
     * @param customer the CustomerRequest DTO.
     * @return the created Customer domain object.
     */
    @Mapping(target = "type", source = "type")
    @Mapping(target = "id", source = "customer", qualifiedByName = "generateUUID")
    Customer created(CustomerRequest customer);

    /**
     * Maps a reactive Mono of CustomerRequest to a Mono of Customer domain object.
     *
     * @param customerMono the Mono containing the CustomerRequest.
     * @return a Mono emitting the mapped Customer domain object.
     */
    default Mono<Customer> map(Mono<CustomerRequest> customerMono) {
        return customerMono.map(this::created);
    }

    /**
     * Generates a UUID for a Customer object.
     *
     * @param customer the CustomerRequest DTO.
     * @return a randomly generated UUID.
     */
    @Named("generateUUID")
    default String generateUuid(CustomerRequest customer) {
        return UUID.randomUUID().toString();
    }
}
