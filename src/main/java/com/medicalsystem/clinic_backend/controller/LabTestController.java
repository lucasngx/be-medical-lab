package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.LabTest;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.LabTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab-tests")
@RequiredArgsConstructor
public class LabTestController {
    private final LabTestService labTestService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<LabTest>> getAllLabTests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(labTestService.getAllLabTests(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTest> getLabTestById(@PathVariable Long id) {
        return ResponseEntity.ok(labTestService.getLabTestById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<LabTest>> getLabTestsByPatientId(@PathVariable Long patientId) {
        return ResponseEntity.ok(labTestService.getLabTestsByPatientId(patientId));
    }

    @PostMapping
    public ResponseEntity<LabTest> createLabTest(@RequestBody LabTest labTest) {
        return ResponseEntity.ok(labTestService.createLabTest(labTest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabTest> updateLabTest(@PathVariable Long id, @RequestBody LabTest labTest) {
        return ResponseEntity.ok(labTestService.updateLabTest(id, labTest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabTest(@PathVariable Long id) {
        labTestService.deleteLabTest(id);
        return ResponseEntity.ok().build();
    }
}
