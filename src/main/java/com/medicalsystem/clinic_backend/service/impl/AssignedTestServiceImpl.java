package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import com.medicalsystem.clinic_backend.repository.AssignedTestRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.AssignedTestService;
import com.medicalsystem.clinic_backend.service.ExaminationService;
import com.medicalsystem.clinic_backend.service.LabTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignedTestServiceImpl implements AssignedTestService {
    private final AssignedTestRepository assignedTestRepository;
    private final ExaminationService examinationService;
    private final LabTestService labTestService;

    @Override
    public PaginatedResponse<AssignedTest> getAllAssignedTests(Pageable pageable) {
        Page<AssignedTest> page = assignedTestRepository.findAll(pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public AssignedTest getAssignedTestById(Long id) {
        return assignedTestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("AssignedTest not found with id: " + id));
    }

    @Override
    @Transactional
    public AssignedTest createAssignedTest(AssignedTest assignedTest) {
        assignedTest.setCreatedAt(new Date());
        assignedTest.setUpdatedAt(new Date());
        assignedTest.setStatus(TestStatus.PENDING);
        return assignedTestRepository.save(assignedTest);
    }

    @Override
    @Transactional
    public AssignedTest updateAssignedTest(Long id, AssignedTest assignedTestDetails) {
        AssignedTest assignedTest = getAssignedTestById(id);

        assignedTest.setStatus(assignedTestDetails.getStatus());
        assignedTest.setResult(assignedTestDetails.getResult());
        assignedTest.setNotes(assignedTestDetails.getNotes());
        assignedTest.setUpdatedAt(new Date());

        return assignedTestRepository.save(assignedTest);
    }

    @Override
    @Transactional
    public void deleteAssignedTest(Long id) {
        AssignedTest assignedTest = getAssignedTestById(id);
        assignedTestRepository.delete(assignedTest);
    }

    @Override
    @Transactional
    public AssignedTest assignLabTest(AssignedTest assignedTest) {
        if (assignedTest.getExamination() == null || assignedTest.getExamination().getId() == null) {
            throw new IllegalArgumentException("AssignedTest must include a valid examination.");
        }

        Examination examination = examinationService.getExaminationById(assignedTest.getExamination().getId());

        if (examination.getStatus() == ExaminationStatus.SCHEDULED) {
            examinationService.updateExaminationStatus(examination.getId(), ExaminationStatus.IN_PROGRESS);
        }

        return createAssignedTest(assignedTest);
    }

    @Override
    @Transactional
    public AssignedTest updateAssignedTestStatus(Long id, TestStatus status) {
        AssignedTest assignedTest = getAssignedTestById(id);
        assignedTest.setStatus(status);
        return assignedTestRepository.save(assignedTest);
    }

    @Override
    public List<AssignedTest> getAssignedTestsByExaminationId(Long examinationId) {
        return assignedTestRepository.findByExaminationId(examinationId);
    }

    @Override
    public PaginatedResponse<AssignedTest> getAssignedTestsByStatus(int page, int limit, TestStatus status) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<AssignedTest> pageResult = assignedTestRepository.findByStatus(pageable, status);
        return new PaginatedResponse<>(
            pageResult.getContent(),
            pageResult.getTotalElements(),
            pageResult.getNumber(),
            pageResult.getSize()
        );
    }

    @Override
    public PaginatedResponse<AssignedTest> searchAssignedTests(String searchTerm, TestStatus status, Pageable pageable) {
        Page<AssignedTest> page = assignedTestRepository.searchAssignedTests(searchTerm, status, pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public List<AssignedTest> getAssignedTestsByPatientId(Long patientId) {
        return assignedTestRepository.findByPatientId(patientId);
    }

    @Override
    public List<AssignedTest> getAssignedTestsByTechnicianId(Long technicianId) {
        return assignedTestRepository.findByTechnicianId(technicianId);
    }
} 