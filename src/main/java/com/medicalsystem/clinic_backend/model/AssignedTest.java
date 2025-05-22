package com.medicalsystem.clinic_backend.model;

import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class AssignedTest {
    private Long id;
    private TestStatus status;
    private Date assignedDate;
    private Examination examination;
    private LabTest labTest;

    public AssignedTest() {
    }

    public AssignedTest(Long id, TestStatus status, Date assignedDate, Examination examination, LabTest labTest) {
        this.id = id;
        this.status = status;
        this.assignedDate = assignedDate;
        this.examination = examination;
        this.labTest = labTest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestStatus getStatus() {
        return status;
    }

    public void setStatus(TestStatus status) {
        this.status = status;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate = assignedDate;
    }

    public Examination getExamination() {
        return examination;
    }

    public void setExamination(Examination examination) {
        this.examination = examination;
    }

    public LabTest getLabTest() {
        return labTest;
    }

    public void setLabTest(LabTest labTest) {
        this.labTest = labTest;
    }
}