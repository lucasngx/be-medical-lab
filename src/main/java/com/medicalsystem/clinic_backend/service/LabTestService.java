package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.LabTest;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.repository.LabTestRepository;
import com.medicalsystem.clinic_backend.response.ExaminationResponse;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LabTestService {
    // Basic CRUD operations
    PaginatedResponse<LabTest> getAllLabTests(Pageable pageable);
    LabTest getLabTestById(Long id);
    LabTest createLabTest(LabTest labTest);
    LabTest updateLabTest(Long id, LabTest labTest);
    void deleteLabTest(Long id);

    // Query operations
    List<LabTest> searchLabTestsByName(String name);
}