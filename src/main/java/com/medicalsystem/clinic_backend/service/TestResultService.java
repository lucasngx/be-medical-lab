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

@Service
@RequiredArgsConstructor
public class TestResultService {
    private final TestResultRepository testResultRepository;
    private final AssignedTestService assignedTestService;

    public PaginatedResponse<TestResult> getPaginatedTestResults(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<TestResult> pageResult = testResultRepository.findAll(pageable);

        PaginatedResponse<TestResult> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        return response;
    }

    public TestResult getTestResultById(Long id) {
        return testResultRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Test Result not found with id: " + id));
    }

    public TestResult getTestResultByAssignedTestId(Long assignedTestId) {
        return testResultRepository.findByAssignedTestId(assignedTestId)
                .orElseThrow(() -> new ResourceNotFoundException("Test Result not found for assigned test id: " + assignedTestId));
    }

    public List<TestResult> getTestResultsByTechnicianId(Long technicianId) {
        return testResultRepository.findByTechnicianId(technicianId);
    }

    public List<TestResult> getTestResultsByStatus(ResultStatus status) {
        return testResultRepository.findByStatus(status);
    }

    @Transactional
    public TestResult createTestResult(TestResult testResult) {
        // Check if testResult has an assignedTest
        if (testResult.getAssignedTest() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AssignedTest is required");
        }

        // Check if assignedTest has an ID
        if (testResult.getAssignedTest().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AssignedTest ID is required");
        }

        // Update the corresponding assigned test status
        Long assignedTestId = testResult.getAssignedTest().getId();
        AssignedTest assignedTest = assignedTestService.getAssignedTestById(assignedTestId);

        // Set the fully loaded entity back to the test result
        testResult.setAssignedTest(assignedTest);

        // Update status
        assignedTestService.updateAssignedTestStatus(assignedTest.getId(), TestStatus.COMPLETED);

        testResult.setStatus(ResultStatus.DRAFT);
        testResult.setResultDate(new Date());
        return testResultRepository.save(testResult);
    }

    public TestResult updateTestResult(Long id, TestResult testResultDetails) {
        TestResult testResult = getTestResultById(id);

        testResult.setResultData(testResultDetails.getResultData());
        testResult.setComment(testResultDetails.getComment());

        return testResultRepository.save(testResult);
    }

    public TestResult updateTestResultStatus(Long id, ResultStatus status) {
        TestResult testResult = getTestResultById(id);
        testResult.setStatus(status);
        return testResultRepository.save(testResult);
    }
}