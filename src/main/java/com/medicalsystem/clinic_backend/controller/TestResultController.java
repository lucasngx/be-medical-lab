package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.TestResult;
import com.medicalsystem.clinic_backend.model.enums.ResultStatus;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.TestResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/test-results")
@RequiredArgsConstructor
public class TestResultController {
    private final TestResultService testResultService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<TestResult>> getAllTestResults(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        PaginatedResponse<TestResult> response = testResultService.getPaginatedTestResults(page, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResult> getTestResultById(@PathVariable Long id) {
        TestResult testResult = testResultService.getTestResultById(id);
        return ResponseEntity.ok(testResult);
    }

    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<TestResult>> getTestResultsByTechnician(@PathVariable Long technicianId) {
        List<TestResult> testResults = testResultService.getTestResultsByTechnicianId(technicianId);
        return ResponseEntity.ok(testResults);
    }

    @GetMapping("/assigned-test/{assignedTestId}")
    public ResponseEntity<TestResult> getTestResultByAssignedTest(@PathVariable Long assignedTestId) {
        TestResult testResult = testResultService.getTestResultByAssignedTestId(assignedTestId);
        return ResponseEntity.ok(testResult);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<TestResult>> getTestResultsByStatus(@PathVariable ResultStatus status) {
        List<TestResult> testResults = testResultService.getTestResultsByStatus(status);
        return ResponseEntity.ok(testResults);
    }

    @PostMapping
    public ResponseEntity<TestResult> createTestResult(@Valid @RequestBody TestResult testResult) {
        TestResult createdTestResult = testResultService.createTestResult(testResult);
        return new ResponseEntity<>(createdTestResult, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestResult> updateTestResult(
            @PathVariable Long id,
            @Valid @RequestBody TestResult testResult) {
        TestResult updatedTestResult = testResultService.updateTestResult(id, testResult);
        return ResponseEntity.ok(updatedTestResult);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TestResult> updateTestResultStatus(
            @PathVariable Long id,
            @RequestParam ResultStatus status) {
        TestResult updatedTestResult = testResultService.updateTestResultStatus(id, status);
        return ResponseEntity.ok(updatedTestResult);
    }
}