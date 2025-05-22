package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExamStatus;
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

@Service
@RequiredArgsConstructor
public class AssignedTestService {
    private final AssignedTestRepository assignedTestRepository;
    private final ExaminationService examinationService;
    private final LabTestService labTestService;

    public PaginatedResponse<AssignedTest> getPaginatedAssignedTests(int page, int limit, TestStatus status) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<AssignedTest> pageResult = assignedTestRepository.findByStatus(pageable, status);

        PaginatedResponse<AssignedTest> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);

        return response;
    }

    public AssignedTest getAssignedTestById(Long id) {
        return assignedTestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assigned Test not found with id: " + id));
    }

    public List<AssignedTest> getAssignedTestsByExaminationId(Long examinationId) {
        return assignedTestRepository.findByExaminationId(examinationId);
    }

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

    @Transactional
    public AssignedTest assignLabTest(AssignedTest assignedTest) {
        Examination examination = assignedTest.getExamination();

        if (examination == null || examination.getId() == null) {
            throw new IllegalArgumentException("AssignedTest must include an Examination with a valid ID.");
        }

        Examination fullExamination = examinationService.getExaminationById(examination.getId());

        if (fullExamination.getStatus() == ExamStatus.PENDING) {
            examinationService.updateExaminationStatus(fullExamination.getId(), ExamStatus.IN_PROGRESS);
        }

        assignedTest.setExamination(fullExamination); // Make sure it's attached
        assignedTest.setStatus(TestStatus.PENDING);
        assignedTest.setAssignedDate(new Date());
        return assignedTestRepository.save(assignedTest);
    }

    public AssignedTest updateAssignedTestStatus(Long id, TestStatus status) {
        AssignedTest assignedTest = getAssignedTestById(id);
        assignedTest.setStatus(status);
        return assignedTestRepository.save(assignedTest);
    }
}