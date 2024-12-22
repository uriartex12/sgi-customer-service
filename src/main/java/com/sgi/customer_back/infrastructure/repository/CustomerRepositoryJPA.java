package com.sgi.customer_back.infrastructure.repository;

import com.sgi.customer_back.domain.model.CustomerEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepositoryJPA extends ReactiveMongoRepository<CustomerEntity,String> {

}
