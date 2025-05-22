package com.medicalsystem.clinic_backend.controller;

import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExamStatus;
import com.medicalsystem.clinic_backend.response.ExaminationResponse;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/examinations")
@RequiredArgsConstructor
public class ExaminationController {
    private final ExaminationService examinationService;

    @GetMapping
    public ResponseEntity<PaginatedResponse<ExaminationResponse>> getAllExaminations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "PENDING") ExamStatus status) {

        PaginatedResponse<ExaminationResponse> response = examinationService.getPaginatedExaminations(page, limit, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Examination> getExaminationById(@PathVariable Long id) {
        Examination examination = examinationService.getExaminationById(id);
        return ResponseEntity.ok(examination);
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Examination>> getExaminationsByPatientId(@PathVariable Long patientId) {
        List<Examination> examinations = examinationService.getExaminationsByPatientId(patientId);
        return ResponseEntity.ok(examinations);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<Examination>> getExaminationsByDoctorId(@PathVariable Long doctorId) {
        List<Examination> examinations = examinationService.getExaminationsByDoctorId(doctorId);
        return ResponseEntity.ok(examinations);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<PaginatedResponse<Examination>> getExaminationsByStatus(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(defaultValue = "PENDING") ExamStatus status) {
        PaginatedResponse<Examination> response = examinationService.getExaminationsByStatus(page, limit, status);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Examination> createExamination(@Valid @RequestBody Examination examination) {
        Examination savedExamination = examinationService.createExamination(examination);
        return new ResponseEntity<>(savedExamination, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Examination> updateExamination(@PathVariable Long id, @Valid @RequestBody Examination examination) {
        Examination updatedExamination = examinationService.updateExamination(id, examination);
        return ResponseEntity.ok(updatedExamination);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Examination> updateExaminationStatus(@PathVariable Long id, @RequestParam ExamStatus status) {
        Examination updatedExamination = examinationService.updateExaminationStatus(id, status);
        return ResponseEntity.ok(updatedExamination);
    }
}
