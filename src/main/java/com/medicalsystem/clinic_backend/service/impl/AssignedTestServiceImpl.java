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
        Page<AssignedTest> pageResult = assignedTestRepository.findAll(pageable);
        
        PaginatedResponse<AssignedTest> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(pageable.getPageNumber() + 1);
        response.setLimit(pageable.getPageSize());
        return response;
    }

    @Override
    public AssignedTest getAssignedTestById(Long id) {
        return assignedTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assigned Test not found with id: " + id));
    }

    @Override
    @Transactional
    public AssignedTest createAssignedTest(AssignedTest assignedTest) {
        assignedTest.setStatus(TestStatus.PENDING);
        assignedTest.setAssignedDate(new Date());
        return assignedTestRepository.save(assignedTest);
    }

    @Override
    @Transactional
    public AssignedTest updateAssignedTest(Long id, AssignedTest assignedTest) {
        AssignedTest existingTest = getAssignedTestById(id);
        
        // Update only the fields that should be updatable
        existingTest.setStatus(assignedTest.getStatus());
        existingTest.setAssignedDate(assignedTest.getAssignedDate());
        
        return assignedTestRepository.save(existingTest);
    }

    @Override
    @Transactional
    public void deleteAssignedTest(Long id) {
        if (!assignedTestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Assigned Test not found with id: " + id);
        }
        assignedTestRepository.deleteById(id);
    }

    @Override
    @Transactional
    public AssignedTest assignLabTest(AssignedTest assignedTest) {
        if (assignedTest.getExaminationId() == null) {
            throw new IllegalArgumentException("AssignedTest must include a valid examination ID.");
        }

        Examination examination = examinationService.getExaminationById(assignedTest.getExaminationId());

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

        PaginatedResponse<AssignedTest> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        return response;
    }

    @Override
    public PaginatedResponse<AssignedTest> searchAssignedTests(String patientName, ExaminationStatus status, Pageable pageable) {
        Page<AssignedTest> page = assignedTestRepository.searchAssignedTests(patientName, status, pageable);
        
        PaginatedResponse<AssignedTest> response = new PaginatedResponse<>();
        response.setData(page.getContent());
        response.setTotal(page.getTotalElements());
        response.setPage(pageable.getPageNumber() + 1);
        response.setLimit(pageable.getPageSize());
        return response;
    }
} 