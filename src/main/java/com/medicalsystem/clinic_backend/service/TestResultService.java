package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.Doctor;
import com.medicalsystem.clinic_backend.model.TestResult;
import com.medicalsystem.clinic_backend.model.enums.ResultStatus;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import com.medicalsystem.clinic_backend.repository.TestResultRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

public interface TestResultService {
    // Basic CRUD operations
    PaginatedResponse<TestResult> getAllTestResults(Pageable pageable);
    TestResult getTestResultById(Long id);
    TestResult createTestResult(TestResult testResult);
    TestResult updateTestResult(Long id, TestResult testResult);
    void deleteTestResult(Long id);

    // Business operations
    TestResult updateTestResultStatus(Long id, ResultStatus status);
    
    // Query operations
    TestResult getTestResultByAssignedTestId(Long assignedTestId);
    List<TestResult> getTestResultsByTechnicianId(Long technicianId);
    List<TestResult> getTestResultsByStatus(ResultStatus status);
}