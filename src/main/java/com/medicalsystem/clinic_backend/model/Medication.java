package com.medicalsystem.clinic_backend.model;

public class Medication {
    private Long id;
    private String name;
    private String description;
    private String dosage;
    private String frequency;
    private String duration;

    public Medication() {
    }

    public Medication(Long id, String name, String description, String dosage, String frequency, String duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dosage = dosage;
        this.frequency = frequency;
        this.duration = duration;
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

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}