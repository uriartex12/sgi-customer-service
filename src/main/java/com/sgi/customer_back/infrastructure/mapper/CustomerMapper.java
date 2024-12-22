package com.sgi.customer_back.infrastructure.mapper;


import com.sgi.customer_back.domain.model.CustomerEntity;

import com.sgi.customer_back.infrastructure.dto.CustomerRequest;
import com.sgi.customer_back.infrastructure.dto.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Mapper
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "type", source = "type")
    CustomerResponse map(CustomerEntity customerEntity);

    @Mapping(target = "id", ignore = true)
    CustomerEntity map(CustomerRequest customer, String id);

    @Mapping(target = "type", source = "type")
    @Mapping(target = "id", source = "customer", qualifiedByName = "generateUUID")
    CustomerEntity created(CustomerRequest customer);

    default Mono<CustomerEntity> map(Mono<CustomerRequest> customerMono) {
        return customerMono.map(this::created);
    }
    @Named("generateUUID")
    default String generateUUID(CustomerRequest customer) {
        return UUID.randomUUID().toString();
    }
}

