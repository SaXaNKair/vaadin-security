package com.example.vaadinsecurity.model;


import com.vaadin.annotations.AutoGenerated;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {

    @Id
    @AutoGenerated
    private Long role_id;

    private String role;

    public Role() {
    }

    public Long getId() {
        return role_id;
    }

    public void setId(Long id) {
        this.role_id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static String[] getAllRoles() {
        return new String[] {USER, ADMIN };
    }

    public static final String USER = "ROLE_USER";
    public static final String ADMIN = "ROLE_ADMIN";
}