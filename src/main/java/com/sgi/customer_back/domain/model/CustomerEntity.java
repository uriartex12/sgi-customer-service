package com.sgi.customer_back.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Document(collection = "customer")
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndex(def = "{'id': 1, 'documentId': 1}", name = "id_document_index", unique = true)
public class CustomerEntity {

    @Id
    private String id;
    private String name;
    private String type;
    private String documentId;
    private String email;
    private String phoneNumber;
    private String address;
}