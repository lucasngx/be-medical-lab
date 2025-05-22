package com.medicalsystem.clinic_backend.service;

import com.medicalsystem.clinic_backend.exception.ResourceNotFoundException;
import com.medicalsystem.clinic_backend.model.Examination;
import com.medicalsystem.clinic_backend.model.enums.ExamStatus;
import com.medicalsystem.clinic_backend.repository.ExaminationRepository;
import com.medicalsystem.clinic_backend.response.PaginatedResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExaminationService {
    private final ExaminationRepository examinationRepository;

    public PaginatedResponse<Examination> getPaginatedExaminations(int page, int limit, ExamStatus status) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Examination> pageResult = examinationRepository.findByStatus(pageable, status);

        PaginatedResponse<Examination> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        return response;
    }

    public Examination getExaminationById(Long id) {
        return examinationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examination not found with id: " + id));
    }

    public List<Examination> getExaminationsByPatientId(Long patientId) {
        return examinationRepository.findByPatientId(patientId);
    }

    public List<Examination> getExaminationsByDoctorId(Long doctorId) {
        return examinationRepository.findByDoctorId(doctorId);
    }

    public PaginatedResponse<Examination> getExaminationsByStatus(int page, int limit, ExamStatus status) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Examination> pageResult = examinationRepository.findByStatus(pageable, status);

        PaginatedResponse<Examination> response = new PaginatedResponse<>();
        response.setData(pageResult.getContent());
        response.setTotal(pageResult.getTotalElements());
        response.setPage(page);
        response.setLimit(limit);
        return response;
    }

    public Examination createExamination(Examination examination) {
        examination.setStatus(ExamStatus.PENDING);
        examination.setExamDate(new Date());
        return examinationRepository.save(examination);
    }

    public Examination updateExamination(Long id, Examination examinationDetails) {
        Examination examination = getExaminationById(id);

        examination.setSymptoms(examinationDetails.getSymptoms());
        examination.setNotes(examinationDetails.getNotes());
        examination.setStatus(examinationDetails.getStatus());

        return examinationRepository.save(examination);
    }

    public Examination updateExaminationStatus(Long id, ExamStatus status) {
        Examination examination = getExaminationById(id);
        examination.setStatus(status);
        return examinationRepository.save(examination);
    }
}