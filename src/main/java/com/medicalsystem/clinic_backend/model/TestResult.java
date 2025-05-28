package com.medicalsystem.clinic_backend.model;

import com.medicalsystem.clinic_backend.model.enums.ResultStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "test_results")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "technician_id", nullable = false)
    private Technician technician;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private LabTest test;

    @Column(name = "assigned_test_id", nullable = false)
    private Long assignedTestId;

    @Column(name = "result_data", columnDefinition = "TEXT")
    private String resultData;

    @Column(nullable = false)
    private String result;

    @Column(nullable = false)
    private String notes;

    @Column(name = "result_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date resultDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultStatus status;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        resultDate = new Date();
        status = ResultStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}