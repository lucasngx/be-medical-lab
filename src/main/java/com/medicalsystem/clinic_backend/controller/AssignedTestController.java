package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.AssignedTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/assigned-tests")
@RequiredArgsConstructor
public class AssignedTestController {
    private final AssignedTestService assignedTestService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<AssignedTest>> getPaginatedAssignedTests(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "PENDING") TestStatus status) {

        PaginatedResponse<AssignedTest> response = assignedTestService.getPaginatedAssignedTests(page, limit, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignedTest> getAssignedTestById(@PathVariable Long id) {
        AssignedTest assignedTest = assignedTestService.getAssignedTestById(id);
        return ResponseEntity.ok(assignedTest);
    }

    @GetMapping("/examination/{examinationId}")
    public ResponseEntity<List<AssignedTest>> getAssignedTestsByExaminationId(@PathVariable Long examinationId) {
        List<AssignedTest> assignedTests = assignedTestService.getAssignedTestsByExaminationId(examinationId);
        return ResponseEntity.ok(assignedTests);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PaginatedResponse<AssignedTest>> getExaminationsByStatus(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "PENDING") TestStatus status) {
        PaginatedResponse<AssignedTest> response = assignedTestService.getAssignedTestsByStatus(page, limit, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AssignedTest> assignLabTest(@Valid @RequestBody AssignedTest assignedTest) {
        AssignedTest savedAssignedTest = assignedTestService.assignLabTest(assignedTest);
        return new ResponseEntity<>(savedAssignedTest, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<AssignedTest> updateAssignedTestStatus(@PathVariable Long id, @RequestParam TestStatus status) {
        AssignedTest updatedAssignedTest = assignedTestService.updateAssignedTestStatus(id, status);
        return ResponseEntity.ok(updatedAssignedTest);
    }
}