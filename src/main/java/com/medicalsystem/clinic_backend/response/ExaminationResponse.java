package com.medicalsystem.clinic_backend.response;

import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.Patient;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;

import java.util.Date;

public class ExaminationResponse {
    private Long id;
    private Date examinationDate;
    private String symptoms;
    private ExaminationStatus status;
    private Date createdAt;
    private Date updatedAt;
    private Patient patient;
    private Doctor doctor;
    private Long patientId;
    private Long doctorId;

    public ExaminationResponse() {
    }

    public ExaminationResponse(Long id, Date examinationDate, String symptoms, ExaminationStatus status, Date createdAt, Date updatedAt, Patient patient, Doctor doctor, Long patientId, Long doctorId) {
        this.id = id;
        this.examinationDate = examinationDate;
        this.symptoms = symptoms;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.patient = patient;
        this.doctor = doctor;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExaminationDate() {
        return examinationDate;
    }

    public void setExaminationDate(Date examinationDate) {
        this.examinationDate = examinationDate;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public ExaminationStatus getStatus() {
        return status;
    }

    public void setStatus(ExaminationStatus status) {
        this.status = status;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
