package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.AssignedTest;
import com.medicalsystem.clinic_backend.model.enums.TestStatus;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.AssignedTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assigned-tests")
@RequiredArgsConstructor
public class AssignedTestController {
    private final AssignedTestService assignedTestService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<AssignedTest>> getAllAssignedTests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(assignedTestService.getAllAssignedTests(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignedTest> getAssignedTestById(@PathVariable Long id) {
        return ResponseEntity.ok(assignedTestService.getAssignedTestById(id));
    }

    @PostMapping
    public ResponseEntity<AssignedTest> createAssignedTest(@RequestBody AssignedTest assignedTest) {
        return ResponseEntity.ok(assignedTestService.createAssignedTest(assignedTest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignedTest> updateAssignedTest(
            @PathVariable Long id,
            @RequestBody AssignedTest assignedTestDetails) {
        return ResponseEntity.ok(assignedTestService.updateAssignedTest(id, assignedTestDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignedTest(@PathVariable Long id) {
        assignedTestService.deleteAssignedTest(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/assign")
    public ResponseEntity<AssignedTest> assignLabTest(@RequestBody AssignedTest assignedTest) {
        return ResponseEntity.ok(assignedTestService.assignLabTest(assignedTest));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AssignedTest> updateAssignedTestStatus(
            @PathVariable Long id,
            @RequestParam TestStatus status) {
        return ResponseEntity.ok(assignedTestService.updateAssignedTestStatus(id, status));
    }

    @GetMapping("/examination/{examinationId}")
    public ResponseEntity<List<AssignedTest>> getAssignedTestsByExaminationId(
            @PathVariable Long examinationId) {
        return ResponseEntity.ok(assignedTestService.getAssignedTestsByExaminationId(examinationId));
    }

    @GetMapping("/status")
    public ResponseEntity<PaginatedResponse<AssignedTest>> getAssignedTestsByStatus(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam TestStatus status) {
        return ResponseEntity.ok(assignedTestService.getAssignedTestsByStatus(page, limit, status));
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResponse<AssignedTest>> searchAssignedTests(
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) TestStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(assignedTestService.searchAssignedTests(searchTerm, status, pageable));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AssignedTest>> getAssignedTestsByPatientId(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(assignedTestService.getAssignedTestsByPatientId(patientId));
    }

    @GetMapping("/technician/{technicianId}")
    public ResponseEntity<List<AssignedTest>> getAssignedTestsByTechnicianId(
            @PathVariable Long technicianId) {
        return ResponseEntity.ok(assignedTestService.getAssignedTestsByTechnicianId(technicianId));
    }
}