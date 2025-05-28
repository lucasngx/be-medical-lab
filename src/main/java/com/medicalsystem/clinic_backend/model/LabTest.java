package com.medicalsystem.clinic_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "lab_tests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LabTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<AssignedTest> assignedTests = new ArrayList<>();

    public List<AssignedTest> getAssignedTests() {
        return assignedTests;
    }

    public void setAssignedTests(List<AssignedTest> assignedTests) {
        this.assignedTests = assignedTests;
    }

    public void addAssignedTest(AssignedTest assignedTest) {
        if (!assignedTests.contains(assignedTest)) {
            assignedTests.add(assignedTest);
            if (assignedTest.getTest() != this) {
                assignedTest.setTest(this);
            }
        }
    }

    public void removeAssignedTest(AssignedTest assignedTest) {
        assignedTests.remove(assignedTest);
        if (assignedTest.getTest() == this) {
            assignedTest.setTest(null);
        }
    }

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