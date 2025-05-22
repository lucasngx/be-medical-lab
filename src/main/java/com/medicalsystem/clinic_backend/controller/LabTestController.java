package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.LabTest;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.LabTestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        PaginatedResponse<LabTest> response = labTestService.getPaginatedLabTests(page, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTest> getLabTestById(@PathVariable Long id) {
        LabTest labTest = labTestService.getLabTestById(id);
        return ResponseEntity.ok(labTest);
    }

    @GetMapping("/search")
    public ResponseEntity<List<LabTest>> searchLabTestsByName(@RequestParam String name) {
        List<LabTest> result = labTestService.searchLabTestsByName(name);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<LabTest> createLabTest(@Valid @RequestBody LabTest labTest) {
        LabTest created = labTestService.createLabTest(labTest);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabTest> updateLabTest(@PathVariable Long id, @Valid @RequestBody LabTest labTest) {
        LabTest updated = labTestService.updateLabTest(id, labTest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabTest(@PathVariable Long id) {
        labTestService.deleteLabTest(id);
        return ResponseEntity.noContent().build();
    }
}
