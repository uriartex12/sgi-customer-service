package com.sgi.customer.infrastructure.repository;

import com.sgi.customer.domain.model.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Repository interface for performing reactive CRUD operations on the {@link Customer} entity.
 * Extends {@link ReactiveMongoRepository} to provide default methods for saving, finding,
 * updating, and deleting customers in the MongoDB database.
 * This repository allows interaction with the MongoDB collection that stores customer data.
 */
public interface CustomerRepositoryJpa extends ReactiveMongoRepository<Customer, String> {

}
