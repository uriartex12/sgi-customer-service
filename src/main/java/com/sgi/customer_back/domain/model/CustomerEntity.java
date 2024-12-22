package com.sgi.customer_back.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
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

    public CustomerEntity(String id, String name, String documentId, String type, String email, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.documentId = documentId;
        this.type = type;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}