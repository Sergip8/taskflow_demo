package com.taskflow.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companies")
public class Company {
    public String getId() {
        return id;
    }

    public Company(String id, String name, String nit, String phone, String address, String email) {
        this.id = id;
        this.name = name;
        this.nit = nit;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Id
    private String id;
    private String name;
    private String nit;
    private String phone;
    private String address;
    private String email;

    // Getters y Setters
}
