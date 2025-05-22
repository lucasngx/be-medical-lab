package com.medicalsystem.clinic_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LabTest {
    private Long id;
    private String name;
    private String description;
    private RefRange refRange;

    public LabTest() {
    }

    public LabTest(Long id, String name, String description, RefRange refRange) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.refRange = refRange;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RefRange getRefRange() {
        return refRange;
    }

    public void setRefRange(RefRange refRange) {
        this.refRange = refRange;
    }
}