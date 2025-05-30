package com.medicalsystem.clinic_backend.dto;

import com.medicalsystem.clinic_backend.model.enums.Role;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String email;
    private String name;
    private Role role;
    private Long id;
    private String phone;
    private String department;
    private String specialization;
    private String organizationName;

    public AuthResponse() {
    }

    public AuthResponse(String token, String email, String name, Role role, Long id, String phone, String department, String specialization, String organizationName) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.role = role;
        this.id = id;
        this.phone = phone;
        this.department = department;
        this.specialization = specialization;
        this.organizationName = organizationName;
    }
} 