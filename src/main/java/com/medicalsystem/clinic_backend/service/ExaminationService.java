// This file will become the interface
package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExaminationStatus;
import com.medicalsystem.clinic_backend.repository.ExaminationRepository;
import com.medicalsystem.clinic_backend.response.ExaminationResponse;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public interface ExaminationService {
    PaginatedResponse<ExaminationResponse> getAllExaminations(Pageable pageable);
    ExaminationResponse convertToResponse(Examination examination);
    Examination getExaminationById(Long id);
    List<Examination> getExaminationsByPatientId(Long patientId);
    List<Examination> getExaminationsByDoctorId(Long doctorId);
    PaginatedResponse<Examination> getExaminationsByStatus(int page, int limit, ExaminationStatus status);
    Examination createExamination(Examination examination);
    Examination updateExamination(Long id, Examination examinationDetails);
    Examination updateExaminationStatus(Long id, ExaminationStatus status);
    void deleteExamination(Long id);
    PaginatedResponse<Examination> searchExaminations(String patientName, ExaminationStatus status, Pageable pageable);
}