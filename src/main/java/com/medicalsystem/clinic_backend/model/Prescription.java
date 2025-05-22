package com.medicalsystem.clinic_backend.model;

import java.util.Date;
import java.util.List;

public class Prescription {
    private Long id;
    private String diagnosis;
    private Date prescriptionDate;
    private String notes;
    private List<PrescriptionItem> prescriptionItems;
    private Date createdAt;
    private Date updatedAt;

    public Prescription() {
    }

    public Prescription(Long id, String diagnosis, Date prescriptionDate, String notes, List<PrescriptionItem> prescriptionItems, Date createdAt, Date updatedAt) {
        this.id = id;
        this.diagnosis = diagnosis;
        this.prescriptionDate = prescriptionDate;
        this.notes = notes;
        this.prescriptionItems = prescriptionItems;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<PrescriptionItem> getPrescriptionItems() {
        return prescriptionItems;
    }

    public void setPrescriptionItems(List<PrescriptionItem> prescriptionItems) {
        this.prescriptionItems = prescriptionItems;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
