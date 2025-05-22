package com.medicalsystem.clinic_backend.model;

import com.medicalsystem.clinic_backend.model.enums.ResultStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class TestResult {
    private Long id;
    private AssignedTest assignedTest;
    private Technician technician;
    private String resultData;
    private Date resultDate;
    private ResultStatus status;
    private String comment;

    public TestResult() {
    }

    public TestResult(Long id, AssignedTest assignedTest, Technician technician, String resultData, Date resultDate, ResultStatus status, String comment) {
        this.id = id;
        this.assignedTest = assignedTest;
        this.technician = technician;
        this.resultData = resultData;
        this.resultDate = resultDate;
        this.status = status;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssignedTest getAssignedTest() {
        return assignedTest;
    }

    public void setAssignedTest(AssignedTest assignedTest) {
        this.assignedTest = assignedTest;
    }

    public Technician getTechnician() {
        return technician;
    }

    public void setTechnician(Technician technician) {
        this.technician = technician;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public Date getResultDate() {
        return resultDate;
    }

    public void setResultDate(Date resultDate) {
        this.resultDate = resultDate;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}