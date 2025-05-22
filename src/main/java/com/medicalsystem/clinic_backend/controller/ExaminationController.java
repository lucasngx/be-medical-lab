package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.response.ExaminationResponse;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
public class ExaminationController {
    private final ExaminationService examinationService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ExaminationResponse>> getAllExaminations(Pageable pageable) {
        return ResponseEntity.ok(examinationService.getAllExaminations(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExaminationResponse> getExaminationById(@PathVariable Long id) {
        Examination examination = examinationService.getExaminationById(id);
        return ResponseEntity.ok(examinationService.convertToResponse(examination));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ExaminationResponse>> getExaminationsByPatientId(@PathVariable Long patientId) {
        List<Examination> examinations = examinationService.getExaminationsByPatientId(patientId);
        List<ExaminationResponse> responses = examinations.stream()
            .map(examinationService::convertToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ExaminationResponse>> getExaminationsByDoctorId(@PathVariable Long doctorId) {
        List<Examination> examinations = examinationService.getExaminationsByDoctorId(doctorId);
        List<ExaminationResponse> responses = examinations.stream()
            .map(examinationService::convertToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PaginatedResponse<ExaminationResponse>> getExaminationsByStatus(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "PENDING") ExaminationStatus status) {
        PaginatedResponse<Examination> response = examinationService.getExaminationsByStatus(page, limit, status);
        List<ExaminationResponse> responses = response.getData().stream()
            .map(examinationService::convertToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(new PaginatedResponse<>(
            responses,
            response.getTotal(),
            response.getPage(),
            response.getLimit()
        ));
    }

    @PostMapping
    public ResponseEntity<ExaminationResponse> createExamination(@Valid @RequestBody Examination examination) {
        Examination created = examinationService.createExamination(examination);
        return ResponseEntity.ok(examinationService.convertToResponse(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExaminationResponse> updateExamination(@PathVariable Long id, @Valid @RequestBody Examination examination) {
        Examination updated = examinationService.updateExamination(id, examination);
        return ResponseEntity.ok(examinationService.convertToResponse(updated));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ExaminationResponse> updateExaminationStatus(
            @PathVariable Long id,
            @RequestParam ExaminationStatus status) {
        Examination updated = examinationService.updateExaminationStatus(id, status);
        return ResponseEntity.ok(examinationService.convertToResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExamination(@PathVariable Long id) {
        examinationService.deleteExamination(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<PaginatedResponse<ExaminationResponse>> searchExaminations(
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) ExaminationStatus status,
            Pageable pageable) {
        PaginatedResponse<Examination> response = examinationService.searchExaminations(patientName, status, pageable);
        List<ExaminationResponse> responses = response.getData().stream()
            .map(examinationService::convertToResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(new PaginatedResponse<>(
            responses,
            response.getTotal(),
            response.getPage(),
            response.getLimit()
        ));
    }
}
