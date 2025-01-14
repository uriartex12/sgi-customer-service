package com.sgi.customer.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents a customer in the system.
 * Contains information such as the customer's personal details, contact information,
 * and unique identifiers.
 * The class is mapped to the 'customer' collection in MongoDB.
 */
@Setter
@Getter
@Builder
@Document(collection = "customer")
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(def = "{'id': 1, 'documentId': 1, 'name': 1}", name = "id_document_documentId_name_index", unique = true)
public class Customer {

    @Id
    private String id;
    private String name;
    private String type;
    private String profile;
    private String documentId;
    private String email;
    private String phoneNumber;
    private String address;
}