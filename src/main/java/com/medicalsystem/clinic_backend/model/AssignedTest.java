package com.medicalsystem.clinic_backend.model;

import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "assigned_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignedTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "examination_id", nullable = false)
    private Examination examination;

    @ManyToOne
    @JoinColumn(name = "lab_test_id", nullable = false)
    private LabTest labTest;

    @ManyToOne
    @JoinColumn(name = "technician_id")
    private Technician technician;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TestStatus status;

    @Column(length = 1000)
    private String result;

    @Column(length = 1000)
    private String notes;

    @Column(name = "assigned_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignedDate;

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
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}