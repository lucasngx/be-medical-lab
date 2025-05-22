package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.TestResult;
import com.medicalsystem.clinic_backend.model.enums.ResultStatus;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import com.medicalsystem.clinic_backend.repository.AssignedTestRepository;
import com.medicalsystem.clinic_backend.repository.TestResultRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestResultServiceImpl implements TestResultService {

    private final TestResultRepository testResultRepository;
    private final AssignedTestRepository assignedTestRepository;

    @Override
    public PaginatedResponse<TestResult> getAllTestResults(Pageable pageable) {
        Page<TestResult> page = testResultRepository.findAll(pageable);
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public TestResult getTestResultById(Long id) {
        return testResultRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("TestResult not found with id: " + id));
    }

    @Override
    @Transactional
    public TestResult createTestResult(TestResult testResult) {
        // Validate assigned test exists
        AssignedTest assignedTest = assignedTestRepository.findById(testResult.getAssignedTestId())
            .orElseThrow(() -> new ResourceNotFoundException("AssignedTest not found with id: " + testResult.getAssignedTestId()));

        // Set initial values
        testResult.setResultDate(new Date());
        testResult.setStatus(ResultStatus.DRAFT);

        // Save test result
        TestResult savedResult = testResultRepository.save(testResult);

        // Update assigned test status
        assignedTest.setStatus(TestStatus.COMPLETED);
        assignedTestRepository.save(assignedTest);

        return savedResult;
    }

    @Override
    @Transactional
    public TestResult updateTestResult(Long id, TestResult testResultDetails) {
        TestResult testResult = getTestResultById(id);

        // Update only the fields that should be updatable
        testResult.setResultData(testResultDetails.getResultData());
        testResult.setResultDate(new Date());
        testResult.setComment(testResultDetails.getComment());

        return testResultRepository.save(testResult);
    }

    @Override
    @Transactional
    public void deleteTestResult(Long id) {
        if (!testResultRepository.existsById(id)) {
            throw new ResourceNotFoundException("TestResult not found with id: " + id);
        }
        testResultRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TestResult updateTestResultStatus(Long id, ResultStatus status) {
        TestResult testResult = getTestResultById(id);
        testResult.setStatus(status);
        return testResultRepository.save(testResult);
    }

    @Override
    public TestResult getTestResultByAssignedTestId(Long assignedTestId) {
        return testResultRepository.findByAssignedTestId(assignedTestId)
            .orElseThrow(() -> new ResourceNotFoundException("TestResult not found for assigned test id: " + assignedTestId));
    }

    @Override
    public List<TestResult> getTestResultsByTechnicianId(Long technicianId) {
        return testResultRepository.findByTechnicianId(technicianId);
    }

    @Override
    public List<TestResult> getTestResultsByStatus(ResultStatus status) {
        return testResultRepository.findByStatus(status);
    }
} 