package com.intership.kitesurfing.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public class Role {

    @Id
    private String id;
    @Indexed(unique = true, dropDups = true)

    private String role;

    public Role() {
    }

    public Role(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}


