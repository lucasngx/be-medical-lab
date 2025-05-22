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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TestStatus status;

    @Column(name = "assigned_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignedDate;

    @Column(name = "examination_id", nullable = false)
    private Long examinationId;

    @Column(name = "lab_test_id", nullable = false)
    private Long labTestId;

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