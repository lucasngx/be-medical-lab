package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.Prescription;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<Prescription>> getAllPrescriptions(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        PaginatedResponse<Prescription> response = prescriptionService.getPaginatedPrescriptions(page, limit);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable Long id) {
        Prescription prescription = prescriptionService.getPrescriptionById(id);
        return ResponseEntity.ok(prescription);
    }

    @GetMapping("/examination/{examinationId}")
    public ResponseEntity<Prescription> getPrescriptionByExaminationId(@PathVariable Long examinationId) {
        Prescription prescription = prescriptionService.getPrescriptionByExaminationId(examinationId);
        return ResponseEntity.ok(prescription);
    }

    @PostMapping
    public ResponseEntity<Prescription> createPrescription(@Valid @RequestBody Prescription prescription) {
        Prescription created = prescriptionService.createPrescriptionWithItems(prescription);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prescription> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody Prescription prescription) {

        Prescription updated = prescriptionService.updatePrescription(id, prescription);
        return ResponseEntity.ok(updated);
    }
}
