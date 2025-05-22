package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import com.medicalsystem.clinic_backend.repository.AssignedTestRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface AssignedTestService {
    // Basic CRUD operations
    PaginatedResponse<AssignedTest> getAllAssignedTests(Pageable pageable);
    AssignedTest getAssignedTestById(Long id);
    AssignedTest createAssignedTest(AssignedTest assignedTest);
    AssignedTest updateAssignedTest(Long id, AssignedTest assignedTestDetails);
    void deleteAssignedTest(Long id);

    // Business operations
    AssignedTest assignLabTest(AssignedTest assignedTest);
    AssignedTest updateAssignedTestStatus(Long id, TestStatus status);
    
    // Query operations
    List<AssignedTest> getAssignedTestsByExaminationId(Long examinationId);
    PaginatedResponse<AssignedTest> getAssignedTestsByStatus(int page, int limit, TestStatus status);
    PaginatedResponse<AssignedTest> searchAssignedTests(String searchTerm, TestStatus status, Pageable pageable);
    List<AssignedTest> getAssignedTestsByPatientId(Long patientId);
    List<AssignedTest> getAssignedTestsByTechnicianId(Long technicianId);
}