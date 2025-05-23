package com.medicalsystem.clinic_backend.service.impl;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.repository.ExaminationRepository;
import com.medicalsystem.clinic_backend.response.ExaminationResponse;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import com.medicalsystem.clinic_backend.service.ExaminationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExaminationServiceImpl implements ExaminationService {
    private final ExaminationRepository examinationRepository;

    @Override
    public PaginatedResponse<ExaminationResponse> getAllExaminations(Pageable pageable) {
        Page<Examination> page = examinationRepository.findAll(pageable);
        List<ExaminationResponse> responses = page.getContent().stream()
            .map(this::convertToResponse)
            .collect(Collectors.toList());
        return new PaginatedResponse<>(
            responses,
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }

    @Override
    public ExaminationResponse convertToResponse(Examination examination) {
        ExaminationResponse response = new ExaminationResponse();
        response.setId(examination.getId());
        response.setPatientId(examination.getPatient().getId());
        response.setDoctorId(examination.getDoctor().getId());
        response.setStatus(examination.getStatus());
        response.setSymptoms(examination.getSymptoms());
        response.setExaminationDate(examination.getExaminationDate());
        response.setCreatedAt(examination.getCreatedAt());
        response.setUpdatedAt(examination.getUpdatedAt());
        return response;
    }

    @Override
    public Examination getExaminationById(Long id) {
        return examinationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Examination not found with id: " + id));
    }

    @Override
    public List<Examination> getExaminationsByPatientId(Long patientId) {
        return examinationRepository.findByPatientId(patientId);
    }

    @Override
    public List<Examination> getExaminationsByDoctorId(Long doctorId) {
        return examinationRepository.findByDoctorId(doctorId);
    }

    @Override
    public PaginatedResponse<Examination> getExaminationsByStatus(int page, int limit, ExaminationStatus status) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Examination> pageResult = examinationRepository.findByStatus(status, pageable);
        PaginatedResponse<Examination> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        return response;
    }

    @Override
    public Examination createExamination(Examination examination) {
        examination.setStatus(ExaminationStatus.SCHEDULED);
        examination.setExaminationDate(new Date());
        examination.setCreatedAt(new Date());
        examination.setUpdatedAt(new Date());
        return examinationRepository.save(examination);
    }

    @Override
    public Examination updateExamination(Long id, Examination examinationDetails) {
        Examination examination = getExaminationById(id);
        examination.setPatient(examinationDetails.getPatient());
        examination.setDoctor(examinationDetails.getDoctor());
        examination.setStatus(examinationDetails.getStatus());
        examination.setSymptoms(examinationDetails.getSymptoms());
        examination.setUpdatedAt(new Date());
        return examinationRepository.save(examination);
    }

    @Override
    public Examination updateExaminationStatus(Long id, ExaminationStatus status) {
        Examination examination = getExaminationById(id);
        examination.setStatus(status);
        examination.setUpdatedAt(new Date());
        return examinationRepository.save(examination);
    }

    @Override
    public void deleteExamination(Long id) {
        Examination examination = getExaminationById(id);
        examinationRepository.delete(examination);
    }

    @Override
    public PaginatedResponse<Examination> searchExaminations(String patientName, ExaminationStatus status, Pageable pageable) {
        Page<Examination> page;
        if (patientName != null && !patientName.isEmpty() && status != null) {
            page = examinationRepository.findByPatientNameContainingIgnoreCaseAndStatus(patientName, status, pageable);
        } else if (patientName != null && !patientName.isEmpty()) {
            page = examinationRepository.findByPatientNameContainingIgnoreCase(patientName, pageable);
        } else if (status != null) {
            page = examinationRepository.findByStatus(status, pageable);
        } else {
            page = examinationRepository.findAll(pageable);
        }
        return new PaginatedResponse<>(
            page.getContent(),
            page.getTotalElements(),
            page.getNumber(),
            page.getSize()
        );
    }
} 