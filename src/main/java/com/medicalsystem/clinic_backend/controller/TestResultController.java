package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.TestResult;
import com.medicalsystem.clinic_backend.model.enums.ResultStatus;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.TestResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test-results")
@RequiredArgsConstructor
public class TestResultController {

    private final TestResultService testResultService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<TestResult>> getAllTestResults(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "resultDate") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort.Direction sortDirection = Sort.Direction.fromString(direction.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        
        return ResponseEntity.ok(testResultService.getAllTestResults(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestResult> getTestResultById(@PathVariable Long id) {
        return ResponseEntity.ok(testResultService.getTestResultById(id));
    }

    @PostMapping
    public ResponseEntity<TestResult> createTestResult(@Valid @RequestBody TestResult testResult) {
        return new ResponseEntity<>(testResultService.createTestResult(testResult), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestResult> updateTestResult(
            @PathVariable Long id,
            @Valid @RequestBody TestResult testResult) {
        return ResponseEntity.ok(testResultService.updateTestResult(id, testResult));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestResult(@PathVariable Long id) {
        testResultService.deleteTestResult(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TestResult> updateTestResultStatus(
            @PathVariable Long id,
            @RequestParam ResultStatus status) {
        return ResponseEntity.ok(testResultService.updateTestResultStatus(id, status));
    }

    @GetMapping("/assigned-test/{assignedTestId}")
    public ResponseEntity<TestResult> getTestResultByAssignedTestId(@PathVariable Long assignedTestId) {
        return ResponseEntity.ok(testResultService.getTestResultByAssignedTestId(assignedTestId));
    }

    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<TestResult>> getTestResultsByTechnicianId(@PathVariable Long technicianId) {
        return ResponseEntity.ok(testResultService.getTestResultsByTechnicianId(technicianId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<TestResult>> getTestResultsByStatus(@RequestParam ResultStatus status) {
        return ResponseEntity.ok(testResultService.getTestResultsByStatus(status));
    }
}